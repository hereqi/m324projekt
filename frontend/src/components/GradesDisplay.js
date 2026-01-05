import React, { useState, useEffect, useCallback } from 'react';
import { criterionProgressService } from '../services/criterionProgressService';
import { criterionService } from '../services/criterionService';
import './GradesDisplay.css';

function GradesDisplay({ personId }) {
  const [grades, setGrades] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const loadGrades = useCallback(async () => {
    if (!personId) return;
    
    try {
      setLoading(true);
      const [progressData, criteriaData] = await Promise.all([
        criterionProgressService.getByPersonId(personId),
        criterionService.getAll(),
      ]);
      
      // Berechne Noten
      const teil1Criteria = criteriaData.filter(c => c.teil === 'Teil1');
      const teil2Criteria = criteriaData.filter(c => c.teil === 'Teil2');
      
      const teil1Guetestufen = teil1Criteria.map(criterion => {
        const progress = progressData.find(p => p.criterionId === criterion.id);
        return progress?.guetestufe ?? 0;
      });
      
      const teil2Guetestufen = teil2Criteria.map(criterion => {
        const progress = progressData.find(p => p.criterionId === criterion.id);
        return progress?.guetestufe ?? 0;
      });
      
      // Berechne Durchschnittsnote (vereinfacht: Gütestufe 0-3 → Note 1.0-6.0)
      const calculateNote = (guetestufen) => {
        if (guetestufen.length === 0) return null;
        
        // Gütestufe 0 = 1.0, 1 = 2.5, 2 = 4.0, 3 = 6.0
        const guetestufeToNote = (g) => {
          if (g === 0) return 1.0;
          if (g === 1) return 2.5;
          if (g === 2) return 4.0;
          if (g === 3) return 6.0;
          return 1.0;
        };
        
        const sum = guetestufen.reduce((acc, g) => acc + guetestufeToNote(g), 0);
        const average = sum / guetestufen.length;
        
        return Math.round(average * 10) / 10; // Auf 1 Dezimalstelle runden
      };
      
      const teil1Note = calculateNote(teil1Guetestufen);
      const teil2Note = calculateNote(teil2Guetestufen);
      
      setGrades({
        teil1: {
          guetestufen: teil1Guetestufen,
          note: teil1Note,
          criteria: teil1Criteria,
        },
        teil2: {
          guetestufen: teil2Guetestufen,
          note: teil2Note,
          criteria: teil2Criteria,
        },
        allProgress: progressData,
      });
      
      setError(null);
    } catch (err) {
      const errorMessage = err.response?.status === 404 ? 'Person nicht gefunden' :
                          err.response?.status >= 500 ? 'Serverfehler. Bitte versuchen Sie es später erneut.' :
                          'Fehler beim Laden der Noten. Bitte versuchen Sie es erneut.';
      setError(errorMessage);
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, [personId]);
  
  useEffect(() => {
    loadGrades();
  }, [loadGrades]);
  
  if (!personId) {
    return (
      <div className="grades-display-container">
        <p className="info-message">Bitte wählen Sie eine Person aus, um die Noten anzuzeigen.</p>
      </div>
    );
  }
  
  if (loading) {
    return (
      <div className="grades-display-container">
        <div className="loading">Lade Noten...</div>
      </div>
    );
  }
  
  if (error) {
    return (
      <div className="grades-display-container">
        <div className="error-message">{error}</div>
      </div>
    );
  }
  
  if (!grades) {
    return null;
  }
  
  return (
    <div className="grades-display-container">
      <h2>Berechnete Noten</h2>
      
      <div className="grades-grid">
        <div className="grade-section">
          <h3>Teil 1</h3>
          <div className="grade-value">
            {grades.teil1.note !== null ? `${grades.teil1.note.toFixed(1)}` : 'N/A'}
          </div>
          <div className="guetestufen-detail">
            {grades.teil1.criteria.map((criterion, index) => {
              const guetestufe = grades.teil1.guetestufen[index] ?? 0;
              return (
                <div key={criterion.id} className="guetestufe-item">
                  <span className="criterion-id">{criterion.id}:</span>
                  <span className="guetestufe-value">Gütestufe {guetestufe}</span>
                </div>
              );
            })}
          </div>
        </div>
        
        <div className="grade-section">
          <h3>Teil 2</h3>
          <div className="grade-value">
            {grades.teil2.note !== null ? `${grades.teil2.note.toFixed(1)}` : 'N/A'}
          </div>
          <div className="guetestufen-detail">
            {grades.teil2.criteria.map((criterion, index) => {
              const guetestufe = grades.teil2.guetestufen[index] ?? 0;
              return (
                <div key={criterion.id} className="guetestufe-item">
                  <span className="criterion-id">{criterion.id}:</span>
                  <span className="guetestufe-value">Gütestufe {guetestufe}</span>
                </div>
              );
            })}
          </div>
        </div>
      </div>
      
      <div className="guetestufe-legend">
        <h4>Gütestufen-Legende:</h4>
        <ul>
          <li><span className="legend-badge" style={{ backgroundColor: '#dc3545' }}>0</span> Nicht erfüllt (Note: 1.0)</li>
          <li><span className="legend-badge" style={{ backgroundColor: '#ffc107' }}>1</span> Teilweise erfüllt (Note: 2.5)</li>
          <li><span className="legend-badge" style={{ backgroundColor: '#17a2b8' }}>2</span> Grösstenteils erfüllt (Note: 4.0)</li>
          <li><span className="legend-badge" style={{ backgroundColor: '#28a745' }}>3</span> Vollständig erfüllt (Note: 6.0)</li>
        </ul>
      </div>
    </div>
  );
}

export default GradesDisplay;

