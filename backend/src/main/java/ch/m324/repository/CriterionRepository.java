package ch.m324.repository;

import ch.m324.model.Criterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriterionRepository extends JpaRepository<Criterion, String> {
    
    List<Criterion> findByTeil(String teil);
}

