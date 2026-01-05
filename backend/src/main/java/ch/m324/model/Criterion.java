package ch.m324.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "criteria")
public class Criterion {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String titel;
    
    @Column(columnDefinition = "TEXT")
    private String leitfrage;
    
    @Column(nullable = false)
    private String teil;
    
    @ElementCollection
    @CollectionTable(name = "criterion_anforderungen", joinColumns = @JoinColumn(name = "criterion_id"))
    @Column(name = "anforderung", columnDefinition = "TEXT")
    private List<String> anforderungen = new ArrayList<>();
    
    // Konstruktoren
    public Criterion() {
    }
    
    public Criterion(String id, String titel, String leitfrage, String teil, List<String> anforderungen) {
        this.id = id;
        this.titel = titel;
        this.leitfrage = leitfrage;
        this.teil = teil;
        this.anforderungen = anforderungen;
    }
    
    // Getter und Setter
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitel() {
        return titel;
    }
    
    public void setTitel(String titel) {
        this.titel = titel;
    }
    
    public String getLeitfrage() {
        return leitfrage;
    }
    
    public void setLeitfrage(String leitfrage) {
        this.leitfrage = leitfrage;
    }
    
    public String getTeil() {
        return teil;
    }
    
    public void setTeil(String teil) {
        this.teil = teil;
    }
    
    public List<String> getAnforderungen() {
        return anforderungen;
    }
    
    public void setAnforderungen(List<String> anforderungen) {
        this.anforderungen = anforderungen;
    }
}

