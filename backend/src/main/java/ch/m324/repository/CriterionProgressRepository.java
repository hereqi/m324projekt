package ch.m324.repository;

import ch.m324.model.CriterionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriterionProgressRepository extends JpaRepository<CriterionProgress, Long> {
    
    List<CriterionProgress> findByPersonId(Long personId);
    
    Optional<CriterionProgress> findByPersonIdAndCriterionId(Long personId, String criterionId);
    
    List<CriterionProgress> findByCriterionId(String criterionId);
}

