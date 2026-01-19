import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useParams, useNavigate } from 'react-router-dom';
import { personService } from './services/personService';
import PersonForm from './components/PersonForm';
import CriterionList from './components/CriterionList';
import GradesDisplay from './components/GradesDisplay';
import './App.css';

function HomePage() {
  const [personen, setPersonen] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  useEffect(() => {
    loadPersonen();
  }, []);
  
  const loadPersonen = async () => {
    try {
      setLoading(true);
      const data = await personService.getAll();
      setPersonen(data);
      setError(null);
    } catch (err) {
      setError('Fehler beim Laden der Personen');
      // Error wird bereits über setError angezeigt
    } finally {
      setLoading(false);
    }
  };
  
  const handlePersonCreated = (person) => {
    loadPersonen();
  };
  
  if (loading) {
    return <div className="loading">Lade Personen...</div>;
  }
  
  return (
    <div className="home-page">
      <h1>Kriterien Tracker</h1>
      
      <div className="content-section">
        <div className="form-section">
          <PersonForm onPersonCreated={handlePersonCreated} />
        </div>
        
        <div className="personen-section">
          <h2>Personen</h2>
          {error && <div className="error-message">{error}</div>}
          {personen.length === 0 ? (
            <p className="info-message">Noch keine Personen vorhanden. Erstellen Sie eine neue Person.</p>
          ) : (
            <div className="personen-list">
              {personen.map(person => (
                <Link key={person.id} to={`/person/${person.id}`} className="person-card">
                  <div className="person-name">{person.vorname} {person.name}</div>
                  <div className="person-thema">{person.thema}</div>
                  <div className="person-datum">Abgabe: {new Date(person.abgabedatum).toLocaleDateString('de-CH')}</div>
                </Link>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

function PersonDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [person, setPerson] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [gradesUpdateKey, setGradesUpdateKey] = useState(0);
  
  const loadPerson = React.useCallback(async () => {
    try {
      setLoading(true);
      const data = await personService.getById(id);
      setPerson(data);
      setError(null);
    } catch (err) {
      const errorMessage = err.response?.status === 404 ? 'Person nicht gefunden' :
                          err.response?.status >= 500 ? 'Serverfehler. Bitte versuchen Sie es später erneut.' :
                          'Fehler beim Laden der Person. Bitte versuchen Sie es erneut.';
      setError(errorMessage);
      // Error wird bereits über setError angezeigt
    } finally {
      setLoading(false);
    }
  }, [id]);
  
  useEffect(() => {
    loadPerson();
  }, [loadPerson]);
  
  const handleProgressUpdated = () => {
    // Trigger re-render of GradesDisplay
    setGradesUpdateKey(prev => prev + 1);
  };
  
  if (loading) {
    return <div className="loading">Lade Person...</div>;
  }
  
  if (error || !person) {
    return (
      <div className="error-container">
        <div className="error-message">{error || 'Person nicht gefunden'}</div>
        <button onClick={() => navigate('/')} className="back-button">Zurück zur Übersicht</button>
      </div>
    );
  }
  
  return (
    <div className="person-detail-page">
      <div className="page-header">
        <button onClick={() => navigate('/')} className="back-button">← Zurück</button>
        <h1>{person.vorname} {person.name}</h1>
        <div className="person-info">
          <div><strong>Thema:</strong> {person.thema}</div>
          <div><strong>Abgabedatum:</strong> {new Date(person.abgabedatum).toLocaleDateString('de-CH')}</div>
        </div>
      </div>
      
      <div className="detail-content">
        <div className="grades-section">
          <GradesDisplay personId={parseInt(id)} key={gradesUpdateKey} />
        </div>
        
        <div className="criteria-section">
          <CriterionList 
            personId={parseInt(id)} 
            onProgressUpdated={handleProgressUpdated}
          />
        </div>
      </div>
    </div>
  );
}

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/person/:id" element={<PersonDetailPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
