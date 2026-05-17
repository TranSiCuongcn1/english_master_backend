package repository;

import entity.Test;
import entity.TestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<Test, UUID> {
    List<Test> findByCategory(TestCategory category);
    List<Test> findByCategoryAndYear(TestCategory category, Integer year);
    List<Test> findByYear(Integer year);
}
