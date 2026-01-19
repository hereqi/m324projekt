import React, { useState, useEffect } from 'react';
import { criterionService } from '../services/criterionService';
import { criterionProgressService } from '../services/criterionProgressService';
import CriterionCard from './CriterionCard';
import './CriterionList.css';

function CriterionList({ personId, onProgressUpdated }) {
  const [criteria, setCriteria] = useState([]);
  const [progressMap, setProgressMap] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const loadData = React.useCallback(async () => {
    if (!personId) {
      setLoading(false);
      return;
    }
    
    try {
      setLoading(true);
      const [criteriaData, progressData] = await Promise.all([
        criterionService.getAll(),
        criterionProgressService.getByPersonId(personId),
      ]);
      
      setCriteria(criteriaData);
      
      // Erstelle Map für schnellen Zugriff
      const progressMap = {};
      progressData.forEach(progress => {
        progressMap[progress.criterionId] = progress;
      });
      setProgressMap(progressMap);
      
      setError(null);
    } catch (err) {
      const errorMessage = err.response?.status === 404 ? 'Person nicht gefunden' :
                          err.response?.status >= 500 ? 'Serverfehler. Bitte versuchen Sie es später erneut.' :
                          'Fehler beim Laden der Kriterien. Bitte versuchen Sie es erneut.';
      setError(errorMessage);
      // Error wird bereits über setError angezeigt
    } finally {
      setLoading(false);
    }
  }, [personId]);
  
  useEffect(() => {
    loadData();
  }, [loadData]);
  
  const handleProgressUpdate = async (criterionId, erfuellteAnforderungen, notizen) => {
    try {
      const progressData = {
        personId: personId,
        criterionId: criterionId,
        erfuellteAnforderungen: erfuellteAnforderungen,
        notizen: notizen,
      };
      
      await criterionProgressService.saveOrUpdate(progressData);
      
      // Lade Fortschritt neu
      const updatedProgress = await criterionProgressService.getByPersonAndCriterion(personId, criterionId);
      setProgressMap(prev => ({
        ...prev,
        [criterionId]: updatedProgress,
      }));
      
      // Benachrichtige über Update für Noten-Anzeige
      if (onProgressUpdated) {
        onProgressUpdated();
      }
    } catch (err) {
      // Error wird bereits über alert angezeigt
      const errorMessage = err.response?.data?.message || 
                          err.response?.status === 404 ? 'Person oder Kriterium nicht gefunden' :
                          err.response?.status === 400 ? 'Ungültige Daten' :
                          err.response?.status >= 500 ? 'Serverfehler. Bitte versuchen Sie es später erneut.' :
                          'Fehler beim Speichern des Fortschritts. Bitte versuchen Sie es erneut.';
      alert(errorMessage);
      throw err; // Re-throw für Rollback bei Optimistic Updates
    }
  };
  
  if (!personId) {
    return (
      <div className="criterion-list-container">
        <p className="info-message">Bitte wählen Sie zuerst eine Person aus oder erstellen Sie eine neue Person.</p>
      </div>
    );
  }
  
  if (loading) {
    return (
      <div className="criterion-list-container">
        <div className="loading">Lade Kriterien...</div>
      </div>
    );
  }
  
  if (error) {
    return (
      <div className="criterion-list-container">
        <div className="error-message">{error}</div>
      </div>
    );
  }
  
  return (
    <div className="criterion-list-container">
      <h2>Kriterien</h2>
      {criteria.length === 0 ? (
        <p className="info-message">Keine Kriterien gefunden.</p>
      ) : (
        <div className="criteria-grid">
          {criteria.map(criterion => (
            <CriterionCard
              key={criterion.id}
              criterion={criterion}
              progress={progressMap[criterion.id]}
              onProgressUpdate={handleProgressUpdate}
            />
          ))}
        </div>
      )}
    </div>
  );
}

export default CriterionList;

