package ch.m324.repository;

import ch.m324.model.KriteriumErfüllung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KriteriumErfüllungRepository extends JpaRepository<KriteriumErfüllung, Long> {
    List<KriteriumErfüllung> findByPersonId(Long personId);
    Optional<KriteriumErfüllung> findByPersonIdAndKriteriumId(Long personId, String kriteriumId);
}

