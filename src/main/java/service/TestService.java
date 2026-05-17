package service;

import dto.request.CreateTestRequest;
import dto.response.QuestionResponse;
import dto.response.TestResponse;
import entity.Question;
import entity.QuestionOption;
import entity.Test;
import entity.TestCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.QuestionRepository;
import repository.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<TestResponse> getAll(String category, Integer year) {
        List<Test> tests;
        if (category != null && year != null) {
            tests = testRepository.findByCategoryAndYear(TestCategory.valueOf(category), year);
        } else if (category != null) {
            tests = testRepository.findByCategory(TestCategory.valueOf(category));
        } else if (year != null) {
            tests = testRepository.findByYear(year);
        } else {
            tests = testRepository.findAll();
        }
        return tests.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TestResponse getById(UUID id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found with id: " + id));
        return toResponse(test);
    }

    @Transactional
    public TestResponse create(CreateTestRequest request) {
        Test test = new Test();
        test.setTitle(request.getTitle());
        test.setDescription(request.getDescription());
        test.setCategory(TestCategory.valueOf(request.getCategory()));
        test.setYear(request.getYear());
        test.setDurationMinutes(request.getDurationMinutes());
        test.setTotalQuestions(request.getTotalQuestions());

        // Build questions from DTO
        if (request.getQuestions() != null) {
            List<Question> questions = new ArrayList<>();
            int orderIndex = 0;
            for (CreateTestRequest.CreateQuestionRequest qr : request.getQuestions()) {
                Question q = new Question();
                q.setTest(test);
                q.setText(qr.getText());
                q.setCorrectAnswer(qr.getCorrectAnswer());
                q.setExplanation(qr.getExplanation());
                q.setPartNumber(qr.getPart());
                q.setAudioUrl(qr.getAudioUrl());
                q.setImageUrl(qr.getImageUrl());
                q.setPassageText(qr.getPassageText());
                q.setOrderIndex(orderIndex++);

                // Map type string to enum
                if ("text".equalsIgnoreCase(qr.getType())) {
                    q.setType(Question.QuestionType.TEXT);
                } else {
                    q.setType(Question.QuestionType.MULTIPLE_CHOICE);
                }

                // Map skill string to enum
                if (qr.getSkill() != null) {
                    q.setSkill(Question.SkillType.valueOf(qr.getSkill()));
                }

                // Build options
                if (qr.getOptions() != null) {
                    List<QuestionOption> options = new ArrayList<>();
                    int optIdx = 0;
                    for (String optionText : qr.getOptions()) {
                        QuestionOption opt = new QuestionOption();
                        opt.setQuestion(q);
                        opt.setOptionText(optionText);
                        opt.setOrderIndex(optIdx++);
                        options.add(opt);
                    }
                    q.setOptions(options);
                }

                questions.add(q);
            }
            test.setQuestions(questions);
        }

        Test saved = testRepository.save(test);
        return toResponse(saved);
    }

    @Transactional
    public TestResponse update(UUID id, CreateTestRequest request) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found with id: " + id));
        test.setTitle(request.getTitle());
        test.setDescription(request.getDescription());
        test.setCategory(TestCategory.valueOf(request.getCategory()));
        test.setYear(request.getYear());
        test.setDurationMinutes(request.getDurationMinutes());
        test.setTotalQuestions(request.getTotalQuestions());
        return toResponse(testRepository.save(test));
    }

    @Transactional
    public void delete(UUID id) {
        if (!testRepository.existsById(id)) {
            throw new RuntimeException("Test not found with id: " + id);
        }
        testRepository.deleteById(id);
    }

    @Transactional
    public QuestionResponse addQuestionToTest(UUID testId, CreateTestRequest.CreateQuestionRequest request) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found with id: " + testId));
        
        Question q = new Question();
        q.setTest(test);
        
        int nextOrderIndex = test.getQuestions() != null ? test.getQuestions().size() : 0;
        q.setOrderIndex(nextOrderIndex);
        
        mapRequestToQuestion(request, q);
        
        test.setTotalQuestions((test.getTotalQuestions() == null ? 0 : test.getTotalQuestions()) + 1);
        testRepository.save(test);
        
        Question saved = questionRepository.save(q);
        return toQuestionResponse(saved);
    }

    @Transactional
    public QuestionResponse updateQuestion(UUID questionId, CreateTestRequest.CreateQuestionRequest request) {
        Question q = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));
        
        mapRequestToQuestion(request, q);
        
        Question saved = questionRepository.save(q);
        return toQuestionResponse(saved);
    }

    @Transactional
    public void deleteQuestion(UUID questionId) {
        Question q = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));
        
        Test test = q.getTest();
        if (test != null) {
            test.setTotalQuestions(Math.max(0, (test.getTotalQuestions() == null ? 0 : test.getTotalQuestions()) - 1));
            testRepository.save(test);
        }
        
        questionRepository.delete(q);
    }

    private void mapRequestToQuestion(CreateTestRequest.CreateQuestionRequest qr, Question q) {
        q.setText(qr.getText());
        q.setCorrectAnswer(qr.getCorrectAnswer());
        q.setExplanation(qr.getExplanation());
        q.setPartNumber(qr.getPart());
        q.setAudioUrl(qr.getAudioUrl());
        q.setImageUrl(qr.getImageUrl());
        q.setPassageText(qr.getPassageText());

        if ("text".equalsIgnoreCase(qr.getType())) {
            q.setType(Question.QuestionType.TEXT);
        } else {
            q.setType(Question.QuestionType.MULTIPLE_CHOICE);
        }

        if (qr.getSkill() != null) {
            try {
                q.setSkill(Question.SkillType.valueOf(qr.getSkill()));
            } catch (IllegalArgumentException e) {}
        }

        if (qr.getOptions() != null) {
            if (q.getOptions() == null) {
                q.setOptions(new ArrayList<>());
            } else {
                q.getOptions().clear();
            }
            int optIdx = 0;
            for (String optionText : qr.getOptions()) {
                QuestionOption opt = new QuestionOption();
                opt.setQuestion(q);
                opt.setOptionText(optionText);
                opt.setOrderIndex(optIdx++);
                q.getOptions().add(opt);
            }
        }
    }

    private TestResponse toResponse(Test test) {
        List<QuestionResponse> questions = test.getQuestions() != null
                ? test.getQuestions().stream().map(this::toQuestionResponse).collect(Collectors.toList())
                : List.of();

        return TestResponse.builder()
                .id(test.getId().toString())
                .title(test.getTitle())
                .description(test.getDescription())
                .category(test.getCategory().name())
                .year(test.getYear())
                .durationMinutes(test.getDurationMinutes())
                .totalQuestions(test.getTotalQuestions())
                .questions(questions)
                .build();
    }

    public QuestionResponse toQuestionResponse(Question q) {
        List<String> options = q.getOptions() != null
                ? q.getOptions().stream().map(o -> o.getOptionText()).collect(Collectors.toList())
                : List.of();

        String type = q.getType() == Question.QuestionType.MULTIPLE_CHOICE ? "multiple-choice" : "text";

        return QuestionResponse.builder()
                .id(q.getId().toString())
                .type(type)
                .text(q.getText())
                .options(options)
                .correctAnswer(q.getCorrectAnswer())
                .explanation(q.getExplanation())
                .part(q.getPartNumber())
                .audioUrl(q.getAudioUrl())
                .imageUrl(q.getImageUrl())
                .passageText(q.getPassageText())
                .skill(q.getSkill() != null ? q.getSkill().name() : null)
                .build();
    }
}
