package repository;

import entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, UUID> {
    List<TestResult> findByUserIdOrderByCompletedAtDesc(UUID userId);
    Optional<TestResult> findByUserIdAndTestId(UUID userId, UUID testId);

    @Query("SELECT tr.user.id, tr.user.email, tr.user.name, " +
           "COUNT(tr), AVG(tr.score * 100.0 / tr.totalQuestions), SUM(tr.score) " +
           "FROM TestResult tr GROUP BY tr.user.id, tr.user.email, tr.user.name " +
           "ORDER BY SUM(tr.score) DESC")
    List<Object[]> getLeaderboard();
}
