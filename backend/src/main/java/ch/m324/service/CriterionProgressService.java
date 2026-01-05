package ch.m324.service;

import ch.m324.dto.request.CriterionProgressRequest;
import ch.m324.dto.response.CriterionProgressResponse;
import ch.m324.model.CriterionProgress;
import ch.m324.repository.CriterionProgressRepository;
import ch.m324.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CriterionProgressService {
    
    private final CriterionProgressRepository criterionProgressRepository;
    private final PersonRepository personRepository;
    private final GuetestufeService guetestufeService;
    
    public CriterionProgressService(
            CriterionProgressRepository criterionProgressRepository,
            PersonRepository personRepository,
            GuetestufeService guetestufeService) {
        this.criterionProgressRepository = criterionProgressRepository;
        this.personRepository = personRepository;
        this.guetestufeService = guetestufeService;
    }
    
    /**
     * Speichert oder aktualisiert den Fortschritt für ein Kriterium.
     * Berechnet automatisch die Gütestufe.
     */
    public CriterionProgressResponse saveOrUpdateProgress(CriterionProgressRequest request) {
        // Prüfe ob Person existiert
        if (!personRepository.existsById(request.getPersonId())) {
            throw new IllegalArgumentException("Person mit ID " + request.getPersonId() + " existiert nicht");
        }
        
        // Prüfe ob bereits ein Fortschritt existiert
        Optional<CriterionProgress> existingProgress = criterionProgressRepository
            .findByPersonIdAndCriterionId(request.getPersonId(), request.getCriterionId());
        
        CriterionProgress progress;
        if (existingProgress.isPresent()) {
            // Aktualisiere bestehenden Fortschritt
            progress = existingProgress.get();
            progress.setErfuellteAnforderungen(
                request.getErfuellteAnforderungen() != null 
                    ? request.getErfuellteAnforderungen() 
                    : progress.getErfuellteAnforderungen()
            );
            if (request.getNotizen() != null) {
                progress.setNotizen(request.getNotizen());
            }
        } else {
            // Erstelle neuen Fortschritt
            progress = new CriterionProgress();
            progress.setPersonId(request.getPersonId());
            progress.setCriterionId(request.getCriterionId());
            progress.setErfuellteAnforderungen(
                request.getErfuellteAnforderungen() != null 
                    ? request.getErfuellteAnforderungen() 
                    : List.of()
            );
            progress.setNotizen(request.getNotizen());
        }
        
        // Speichere den Fortschritt
        CriterionProgress savedProgress = criterionProgressRepository.save(progress);
        
        // Berechne Gütestufe
        Integer guetestufe = guetestufeService.berechneGuetestufe(savedProgress);
        
        // Konvertiere zu Response DTO
        return mapToResponse(savedProgress, guetestufe);
    }
    
    /**
     * Holt alle Fortschritte für eine Person.
     */
    public List<CriterionProgressResponse> getProgressByPersonId(Long personId) {
        List<CriterionProgress> progressList = criterionProgressRepository.findByPersonId(personId);
        return progressList.stream()
            .map(progress -> {
                Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
                return mapToResponse(progress, guetestufe);
            })
            .collect(Collectors.toList());
    }
    
    /**
     * Holt den Fortschritt für eine Person und ein spezifisches Kriterium.
     */
    public Optional<CriterionProgressResponse> getProgressByPersonAndCriterion(Long personId, String criterionId) {
        Optional<CriterionProgress> progress = criterionProgressRepository
            .findByPersonIdAndCriterionId(personId, criterionId);
        
        return progress.map(p -> {
            Integer guetestufe = guetestufeService.berechneGuetestufe(p);
            return mapToResponse(p, guetestufe);
        });
    }
    
    /**
     * Mappt Entity zu Response DTO.
     */
    private CriterionProgressResponse mapToResponse(CriterionProgress progress, Integer guetestufe) {
        CriterionProgressResponse response = new CriterionProgressResponse();
        response.setId(progress.getId());
        response.setPersonId(progress.getPersonId());
        response.setCriterionId(progress.getCriterionId());
        response.setErfuellteAnforderungen(progress.getErfuellteAnforderungen());
        response.setNotizen(progress.getNotizen());
        response.setGuetestufe(guetestufe);
        return response;
    }
}

