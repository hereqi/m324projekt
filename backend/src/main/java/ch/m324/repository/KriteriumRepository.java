package ch.m324.repository;

import ch.m324.model.Kriterium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KriteriumRepository extends JpaRepository<Kriterium, String> {
}

