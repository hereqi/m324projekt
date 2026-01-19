import React, { useState, useEffect } from 'react';
import './CriterionCard.css';

function CriterionCard({ criterion, progress, onProgressUpdate }) {
  const [checkedItems, setCheckedItems] = useState([]);
  const [notizen, setNotizen] = useState('');
  const [saving, setSaving] = useState(false);
  
  useEffect(() => {
    if (progress) {
      setCheckedItems(progress.erfuellteAnforderungen || []);
      setNotizen(progress.notizen || '');
    } else {
      setCheckedItems([]);
      setNotizen('');
    }
  }, [progress]);
  
  const handleCheckboxChange = async (index) => {
    // Optimistic Update: UI sofort aktualisieren
    const newCheckedItems = checkedItems.includes(index)
      ? checkedItems.filter(i => i !== index)
      : [...checkedItems, index];
    
    // UI sofort aktualisieren
    setCheckedItems(newCheckedItems);
    
    // API-Call im Hintergrund
    try {
      await saveProgress(newCheckedItems, notizen);
    } catch (err) {
      // Rollback bei Fehler
      setCheckedItems(checkedItems);
      // Fehler wird bereits in handleProgressUpdate behandelt
    }
  };
  
  const handleNotizenChange = (e) => {
    const newNotizen = e.target.value;
    setNotizen(newNotizen);
  };
  
  const handleNotizenBlur = async () => {
    try {
      await saveProgress(checkedItems, notizen);
    } catch (err) {
      // Fehler wird bereits in handleProgressUpdate behandelt
    }
  };
  
  const saveProgress = async (erfuellteAnforderungen, notizen) => {
    if (!onProgressUpdate) return;
    
    setSaving(true);
    try {
      await onProgressUpdate(criterion.id, erfuellteAnforderungen, notizen);
    } catch (err) {
      // Error wird bereits in handleProgressUpdate behandelt
    } finally {
      setSaving(false);
    }
  };
  
  const guetestufe = progress?.guetestufe ?? 0;
  const guetestufeLabels = ['Nicht erfüllt', 'Teilweise erfüllt', 'Grösstenteils erfüllt', 'Vollständig erfüllt'];
  const guetestufeColors = ['#dc3545', '#ffc107', '#17a2b8', '#28a745'];
  
  return (
    <div className="criterion-card">
      <div className="criterion-header">
        <h3>{criterion.id}: {criterion.titel}</h3>
        <div className="guetestufe-badge" style={{ backgroundColor: guetestufeColors[guetestufe] }}>
          Gütestufe: {guetestufe} - {guetestufeLabels[guetestufe]}
        </div>
      </div>
      
      {criterion.leitfrage && (
        <div className="leitfrage">
          <strong>Leitfrage:</strong> {criterion.leitfrage}
        </div>
      )}
      
      <div className="teil-badge">Teil: {criterion.teil}</div>
      
      <div className="anforderungen">
        <h4>Anforderungen:</h4>
        {criterion.anforderungen.map((anforderung, index) => (
          <div key={index} className="anforderung-item">
            <label className="checkbox-label">
              <input
                type="checkbox"
                checked={checkedItems.includes(index)}
                onChange={() => handleCheckboxChange(index)}
                disabled={false}
              />
              <span className="checkbox-text">{anforderung}</span>
            </label>
          </div>
        ))}
      </div>
      
      <div className="notizen-section">
        <label htmlFor={`notizen-${criterion.id}`}>Notizen:</label>
        <textarea
          id={`notizen-${criterion.id}`}
          value={notizen}
          onChange={handleNotizenChange}
          onBlur={handleNotizenBlur}
          placeholder="Notizen zu diesem Kriterium..."
          rows="3"
          disabled={false}
        />
      </div>
      
      {saving && <div className="saving-indicator">Speichern...</div>}
      
      <div className="progress-info">
        Erfüllt: {checkedItems.length} / {criterion.anforderungen.length} Anforderungen
      </div>
    </div>
  );
}

export default CriterionCard;

