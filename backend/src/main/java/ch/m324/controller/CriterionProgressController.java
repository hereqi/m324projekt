package ch.m324.controller;

import ch.m324.dto.request.CriterionProgressRequest;
import ch.m324.dto.response.CriterionProgressResponse;
import ch.m324.service.CriterionProgressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criterion-progress")
@CrossOrigin(origins = "*")
public class CriterionProgressController {
    
    private final CriterionProgressService criterionProgressService;
    
    public CriterionProgressController(CriterionProgressService criterionProgressService) {
        this.criterionProgressService = criterionProgressService;
    }
    
    /**
     * GET /api/criterion-progress/person/{personId}
     * Lädt alle Fortschritte für eine Person mit berechneten Gütestufen.
     */
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<CriterionProgressResponse>> getCriterionProgressByPersonId(@PathVariable Long personId) {
        List<CriterionProgressResponse> progressList = criterionProgressService.getProgressByPersonId(personId);
        return ResponseEntity.ok(progressList);
    }
    
    /**
     * GET /api/criterion-progress/person/{personId}/criterion/{criterionId}
     * Lädt den Fortschritt für eine Person und ein spezifisches Kriterium mit berechneter Gütestufe.
     */
    @GetMapping("/person/{personId}/criterion/{criterionId}")
    public ResponseEntity<CriterionProgressResponse> getCriterionProgressByPersonAndCriterion(
            @PathVariable Long personId,
            @PathVariable String criterionId) {
        return criterionProgressService.getProgressByPersonAndCriterion(personId, criterionId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/criterion-progress
     * Speichert oder aktualisiert den Fortschritt für ein Kriterium.
     * Die Gütestufe wird automatisch berechnet.
     */
    @PostMapping
    public ResponseEntity<CriterionProgressResponse> saveOrUpdateProgress(
            @Valid @RequestBody CriterionProgressRequest request) {
        try {
            CriterionProgressResponse response = criterionProgressService.saveOrUpdateProgress(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * PUT /api/criterion-progress
     * Aktualisiert den Fortschritt für ein Kriterium.
     * Die Gütestufe wird automatisch berechnet.
     */
    @PutMapping
    public ResponseEntity<CriterionProgressResponse> updateProgress(
            @Valid @RequestBody CriterionProgressRequest request) {
        try {
            CriterionProgressResponse response = criterionProgressService.saveOrUpdateProgress(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

