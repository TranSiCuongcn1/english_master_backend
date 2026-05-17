package service;

import dto.response.LeaderboardEntryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.TestResultRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final TestResultRepository testResultRepository;

    @Transactional(readOnly = true)
    public List<LeaderboardEntryResponse> getLeaderboard() {
        return testResultRepository.getLeaderboard().stream()
                .map(row -> LeaderboardEntryResponse.builder()
                        .userEmail((String) row[1])
                        .userName((String) row[2])
                        .totalTests(((Number) row[3]).longValue())
                        .avgAccuracy(((Number) row[4]).doubleValue())
                        .totalScore(((Number) row[5]).longValue())
                        .build())
                .collect(Collectors.toList());
    }
}
