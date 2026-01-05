package ch.m324.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CriterionProgressRequest {
    
    @NotNull(message = "Person ID ist erforderlich")
    private Long personId;
    
    @NotNull(message = "Criterion ID ist erforderlich")
    private String criterionId;
    
    private List<Integer> erfuellteAnforderungen;
    
    private String notizen;
    
    // Konstruktoren
    public CriterionProgressRequest() {
    }
    
    public CriterionProgressRequest(Long personId, String criterionId, List<Integer> erfuellteAnforderungen, String notizen) {
        this.personId = personId;
        this.criterionId = criterionId;
        this.erfuellteAnforderungen = erfuellteAnforderungen;
        this.notizen = notizen;
    }
    
    // Getter und Setter
    public Long getPersonId() {
        return personId;
    }
    
    public void setPersonId(Long personId) {
        this.personId = personId;
    }
    
    public String getCriterionId() {
        return criterionId;
    }
    
    public void setCriterionId(String criterionId) {
        this.criterionId = criterionId;
    }
    
    public List<Integer> getErfuellteAnforderungen() {
        return erfuellteAnforderungen;
    }
    
    public void setErfuellteAnforderungen(List<Integer> erfuellteAnforderungen) {
        this.erfuellteAnforderungen = erfuellteAnforderungen;
    }
    
    public String getNotizen() {
        return notizen;
    }
    
    public void setNotizen(String notizen) {
        this.notizen = notizen;
    }
}

