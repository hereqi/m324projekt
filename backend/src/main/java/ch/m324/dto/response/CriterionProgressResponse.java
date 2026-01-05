package ch.m324.dto.response;

import java.util.List;

public class CriterionProgressResponse {
    
    private Long id;
    private Long personId;
    private String criterionId;
    private List<Integer> erfuellteAnforderungen;
    private String notizen;
    private Integer guetestufe;
    
    // Konstruktoren
    public CriterionProgressResponse() {
    }
    
    public CriterionProgressResponse(Long id, Long personId, String criterionId, 
                                     List<Integer> erfuellteAnforderungen, String notizen, Integer guetestufe) {
        this.id = id;
        this.personId = personId;
        this.criterionId = criterionId;
        this.erfuellteAnforderungen = erfuellteAnforderungen;
        this.notizen = notizen;
        this.guetestufe = guetestufe;
    }
    
    // Getter und Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public Integer getGuetestufe() {
        return guetestufe;
    }
    
    public void setGuetestufe(Integer guetestufe) {
        this.guetestufe = guetestufe;
    }
}

