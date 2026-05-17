package service;

import dto.request.SubmitTestRequest;
import dto.response.TestResultResponse;
import entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.QuestionRepository;
import repository.TestRepository;
import repository.TestResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestResultService {

    private final TestResultRepository testResultRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public TestResultResponse submit(User user, SubmitTestRequest request) {
        UUID testId = UUID.fromString(request.getTestId());
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        TestResult result = new TestResult();
        result.setUser(user);
        result.setTest(test);
        result.setTimeTakenSeconds(request.getTimeTakenSeconds());

        int score = 0;
        List<UserAnswer> userAnswers = new ArrayList<>();

        for (SubmitTestRequest.AnswerItem answerItem : request.getAnswers()) {
            UUID questionId = UUID.fromString(answerItem.getQuestionId());
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found: " + answerItem.getQuestionId()));

            boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(answerItem.getSelectedAnswer());
            if (isCorrect) score++;

            UserAnswer ua = new UserAnswer();
            ua.setTestResult(result);
            ua.setQuestion(question);
            ua.setSelectedAnswer(answerItem.getSelectedAnswer());
            ua.setIsCorrect(isCorrect);
            userAnswers.add(ua);
        }

        result.setScore(score);
        result.setTotalQuestions(test.getTotalQuestions());
        result.setAnswers(userAnswers);

        TestResult saved = testResultRepository.save(result);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TestResultResponse> getMyResults(UUID userId) {
        return testResultRepository.findByUserIdOrderByCompletedAtDesc(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TestResultResponse getByTestId(UUID userId, UUID testId) {
        TestResult result = testResultRepository.findByUserIdAndTestId(userId, testId)
                .orElseThrow(() -> new RuntimeException("Result not found"));
        return toResponse(result);
    }

    @Transactional(readOnly = true)
    public List<TestResultResponse> getAll() {
        return testResultRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TestResultResponse toResponse(TestResult r) {
        List<TestResultResponse.UserAnswerResponse> answers = r.getAnswers() != null
                ? r.getAnswers().stream().map(ua -> TestResultResponse.UserAnswerResponse.builder()
                    .questionId(ua.getQuestion().getId().toString())
                    .selectedAnswer(ua.getSelectedAnswer())
                    .isCorrect(ua.getIsCorrect())
                    .build())
                .collect(Collectors.toList())
                : List.of();

        return TestResultResponse.builder()
                .testId(r.getTest().getId().toString())
                .score(r.getScore())
                .totalQuestions(r.getTotalQuestions())
                .timeTakenSeconds(r.getTimeTakenSeconds())
                .answers(answers)
                .date(r.getCompletedAt() != null ? r.getCompletedAt().toString() : null)
                .userEmail(r.getUser().getEmail())
                .build();
    }
}
