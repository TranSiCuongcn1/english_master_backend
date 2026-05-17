package service;

import dto.response.QuestionResponse;
import dto.response.ReadingPassageResponse;
import entity.ReadingPassage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ReadingPassageRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadingService {

    private final ReadingPassageRepository readingPassageRepository;
    private final TestService testService; // reuse question mapping

    @Transactional(readOnly = true)
    public List<ReadingPassageResponse> getPassages(String difficulty, String search) {
        List<ReadingPassage> passages;
        if (difficulty != null && search != null) {
            passages = readingPassageRepository.findByDifficultyAndTitleContainingIgnoreCase(difficulty, search);
        } else if (difficulty != null) {
            passages = readingPassageRepository.findByDifficulty(difficulty);
        } else if (search != null) {
            passages = readingPassageRepository.findByTitleContainingIgnoreCase(search);
        } else {
            passages = readingPassageRepository.findAll();
        }
        return passages.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReadingPassageResponse getById(UUID id) {
        ReadingPassage passage = readingPassageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reading passage not found"));
        return toResponse(passage);
    }

    private ReadingPassageResponse toResponse(ReadingPassage passage) {
        List<QuestionResponse> questions = passage.getQuestions() != null
                ? passage.getQuestions().stream().map(testService::toQuestionResponse).collect(Collectors.toList())
                : List.of();

        return ReadingPassageResponse.builder()
                .id(passage.getId().toString())
                .title(passage.getTitle())
                .text(passage.getText())
                .difficulty(passage.getDifficulty())
                .questions(questions)
                .readingTime(passage.getReadingTime())
                .build();
    }
}
