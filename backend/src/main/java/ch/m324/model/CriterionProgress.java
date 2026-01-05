package ch.m324.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "criterion_progress")
public class CriterionProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long personId;
    
    @Column(nullable = false)
    private String criterionId;
    
    @ElementCollection
    @CollectionTable(name = "erfuellte_anforderungen", joinColumns = @JoinColumn(name = "criterion_progress_id"))
    @Column(name = "anforderung_index")
    private List<Integer> erfuellteAnforderungen = new ArrayList<>();
    
    @Column(columnDefinition = "TEXT")
    private String notizen;
    
    // Konstruktoren
    public CriterionProgress() {
    }
    
    public CriterionProgress(Long personId, String criterionId) {
        this.personId = personId;
        this.criterionId = criterionId;
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
}

