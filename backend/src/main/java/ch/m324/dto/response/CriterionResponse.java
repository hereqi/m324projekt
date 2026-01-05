package ch.m324.dto.response;

import java.util.List;

public class CriterionResponse {
    
    private String id;
    private String titel;
    private String leitfrage;
    private String teil;
    private List<String> anforderungen;
    
    // Konstruktoren
    public CriterionResponse() {
    }
    
    public CriterionResponse(String id, String titel, String leitfrage, String teil, List<String> anforderungen) {
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

