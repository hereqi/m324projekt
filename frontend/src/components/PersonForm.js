import React, { useState } from 'react';
import { personService } from '../services/personService';
import './PersonForm.css';

function PersonForm({ onPersonCreated, existingPerson }) {
  // Formatiere Datum für HTML date input (YYYY-MM-DD)
  const formatDateForInput = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    if (isNaN(date.getTime())) return '';
    return date.toISOString().split('T')[0];
  };
  
  const [formData, setFormData] = useState({
    name: existingPerson?.name || '',
    vorname: existingPerson?.vorname || '',
    thema: existingPerson?.thema || '',
    abgabedatum: formatDateForInput(existingPerson?.abgabedatum) || '',
  });
  
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
    setError(null);
  };
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(null);
    
    try {
      let person;
      if (existingPerson) {
        person = await personService.update(existingPerson.id, formData);
        setSuccess('Person erfolgreich aktualisiert!');
      } else {
        person = await personService.create(formData);
        setSuccess('Person erfolgreich erstellt!');
        if (onPersonCreated) {
          onPersonCreated(person);
        }
        // Formular zurücksetzen
        setFormData({
          name: '',
          vorname: '',
          thema: '',
          abgabedatum: '',
        });
      }
    } catch (err) {
      let errorMessage;
      if (err.response?.data?.message) {
        errorMessage = err.response.data.message;
      } else if (err.response?.status === 404) {
        errorMessage = 'Person nicht gefunden';
      } else if (err.response?.status === 400) {
        errorMessage = 'Ungültige Daten. Bitte überprüfen Sie Ihre Eingaben.';
      } else if (err.response?.status === 409) {
        errorMessage = 'Person existiert bereits';
      } else if (err.response?.status >= 500) {
        errorMessage = 'Serverfehler. Bitte versuchen Sie es später erneut.';
      } else {
        errorMessage = 'Fehler beim Speichern der Person. Bitte versuchen Sie es erneut.';
      }
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <div className="person-form-container">
      <h2>{existingPerson ? 'Person bearbeiten' : 'Neue Person erstellen'}</h2>
      
      <form onSubmit={handleSubmit} className="person-form">
        <div className="form-group">
          <label htmlFor="name">Name *</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
            placeholder="Nachname"
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="vorname">Vorname *</label>
          <input
            type="text"
            id="vorname"
            name="vorname"
            value={formData.vorname}
            onChange={handleChange}
            required
            placeholder="Vorname"
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="thema">Thema *</label>
          <input
            type="text"
            id="thema"
            name="thema"
            value={formData.thema}
            onChange={handleChange}
            required
            placeholder="Projektthema"
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="abgabedatum">Abgabedatum *</label>
          <input
            type="date"
            id="abgabedatum"
            name="abgabedatum"
            value={formData.abgabedatum}
            onChange={handleChange}
            required
          />
        </div>
        
        {error && <div className="error-message">{error}</div>}
        {success && <div className="success-message">{success}</div>}
        
        <button type="submit" disabled={loading} className="submit-button">
          {loading ? 'Speichern...' : existingPerson ? 'Aktualisieren' : 'Erstellen'}
        </button>
      </form>
    </div>
  );
}

export default PersonForm;

