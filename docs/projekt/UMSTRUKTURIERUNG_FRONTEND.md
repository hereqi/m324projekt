# Frontend Umstrukturierungsvorschlag

## Aktuelle Struktur

```
frontend/src/
├── App.css
├── App.js
├── App.test.js
├── index.css
└── index.js
```

## Probleme

1. ❌ Keine Komponenten-Organisation: Alle Dateien im `src/` Root
2. ❌ Keine Trennung von `components`, `services`, `utils`, `hooks`
3. ❌ Keine API-Service-Schicht
4. ❌ Keine Routing-Struktur sichtbar
5. ❌ Keine Assets-Organisation

---

## Empfohlene Struktur

```
frontend/src/
├── index.js                              # Entry Point
├── index.css                             # Global Styles
│
├── App.js                                # Main App Component
├── App.css                               # App Styles
├── App.test.js                           # App Tests
│
├── components/                           # Wiederverwendbare Komponenten
│   ├── common/                           # Gemeinsame Komponenten
│   │   ├── Button/
│   │   │   ├── Button.js
│   │   │   ├── Button.css
│   │   │   └── Button.test.js
│   │   ├── Input/
│   │   │   ├── Input.js
│   │   │   ├── Input.css
│   │   │   └── Input.test.js
│   │   ├── LoadingSpinner/
│   │   │   ├── LoadingSpinner.js
│   │   │   └── LoadingSpinner.css
│   │   └── ErrorMessage/
│   │       ├── ErrorMessage.js
│   │       └── ErrorMessage.css
│   │
│   ├── person/                           # Person-bezogene Komponenten
│   │   ├── PersonList/
│   │   │   ├── PersonList.js
│   │   │   ├── PersonList.css
│   │   │   └── PersonList.test.js
│   │   ├── PersonCard/
│   │   │   ├── PersonCard.js
│   │   │   └── PersonCard.css
│   │   ├── PersonForm/
│   │   │   ├── PersonForm.js
│   │   │   ├── PersonForm.css
│   │   │   └── PersonForm.test.js
│   │   └── PersonDetail/
│   │       ├── PersonDetail.js
│   │       ├── PersonDetail.css
│   │       └── PersonDetail.test.js
│   │
│   └── kriterium/                        # Kriterium-bezogene Komponenten
│       ├── KriteriumList/
│       │   ├── KriteriumList.js
│       │   └── KriteriumList.css
│       ├── KriteriumCard/
│       │   ├── KriteriumCard.js
│       │   └── KriteriumCard.css
│       └── AnforderungCheckbox/
│           ├── AnforderungCheckbox.js
│           └── AnforderungCheckbox.css
│
├── pages/                                # Page-Komponenten
│   ├── HomePage/
│   │   ├── HomePage.js
│   │   └── HomePage.css
│   ├── PersonDetailPage/
│   │   ├── PersonDetailPage.js
│   │   └── PersonDetailPage.css
│   └── NotFoundPage/
│       ├── NotFoundPage.js
│       └── NotFoundPage.css
│
├── services/                             # API-Services
│   ├── api.js                            # Axios-Instanz & Konfiguration
│   ├── personService.js                  # Person-API-Calls
│   ├── kriteriumService.js               # Kriterium-API-Calls
│   └── notenService.js                   # Noten-API-Calls
│
├── hooks/                                # Custom Hooks
│   ├── usePersonen.js                    # Hook für Person-Liste
│   ├── usePerson.js                      # Hook für einzelne Person
│   └── useKriterien.js                   # Hook für Kriterien
│
├── utils/                                # Utility-Funktionen
│   ├── dateUtils.js                      # Datum-Formatierung
│   ├── validation.js                     # Validierungs-Funktionen
│   └── constants.js                      # Konstanten
│
├── context/                              # React Context (falls nötig)
│   └── AppContext.js
│
└── assets/                               # Statische Assets
    ├── images/
    └── fonts/
```

---

## Detaillierte Beschreibung

### 1. Components

**Zweck:** Wiederverwendbare UI-Komponenten

**Organisation:**
- **Feature-basiert:** Komponenten nach Feature gruppiert (`person/`, `kriterium/`)
- **Common:** Gemeinsame Komponenten (`common/`)
- **Komponenten-Ordner:** Jede Komponente hat eigenen Ordner mit JS, CSS, Test

**Beispiel:**
```javascript
// components/person/PersonCard/PersonCard.js
import React from 'react';
import './PersonCard.css';

const PersonCard = ({ person, onClick }) => {
  return (
    <div className="person-card" onClick={onClick}>
      <h3>{person.name} {person.vorname}</h3>
      <p>{person.thema}</p>
    </div>
  );
};

export default PersonCard;
```

---

### 2. Pages

**Zweck:** Vollständige Seiten-Komponenten (Route-Level)

**Best Practices:**
- Pages kombinieren mehrere Komponenten
- Pages enthalten Routing-Logik
- Pages können Container-Komponenten sein

**Beispiel:**
```javascript
// pages/PersonDetailPage/PersonDetailPage.js
import React from 'react';
import { useParams } from 'react-router-dom';
import PersonDetail from '../../components/person/PersonDetail/PersonDetail';
import { usePerson } from '../../hooks/usePerson';

const PersonDetailPage = () => {
  const { id } = useParams();
  const { person, loading, error } = usePerson(id);

  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage message={error} />;

  return <PersonDetail person={person} />;
};

export default PersonDetailPage;
```

---

### 3. Services

**Zweck:** API-Kommunikation, externe Services

**Best Practices:**
- Axios-Instanz zentral konfigurieren
- Service-Funktionen für jeden API-Endpoint
- Error-Handling zentral
- Response-Daten normalisieren

**Beispiel:**
```javascript
// services/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
```

```javascript
// services/personService.js
import api from './api';

export const personService = {
  getAll: async () => {
    const response = await api.get('/personen');
    return response.data;
  },
  
  getById: async (id) => {
    const response = await api.get(`/personen/${id}`);
    return response.data;
  },
  
  create: async (personData) => {
    const response = await api.post('/personen', personData);
    return response.data;
  },
  
  update: async (id, personData) => {
    const response = await api.put(`/personen/${id}`, personData);
    return response.data;
  },
  
  delete: async (id) => {
    await api.delete(`/personen/${id}`);
  },
};
```

---

### 4. Hooks

**Zweck:** Wiederverwendbare State-Logik

**Best Practices:**
- Custom Hooks für wiederkehrende Logik
- API-Calls in Hooks kapseln
- Loading/Error-States verwalten

**Beispiel:**
```javascript
// hooks/usePersonen.js
import { useState, useEffect } from 'react';
import { personService } from '../services/personService';

export const usePersonen = () => {
  const [personen, setPersonen] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPersonen = async () => {
      try {
        setLoading(true);
        const data = await personService.getAll();
        setPersonen(data);
        setError(null);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchPersonen();
  }, []);

  return { personen, loading, error, refetch: fetchPersonen };
};
```

---

### 5. Utils

**Zweck:** Utility-Funktionen, Helper

**Beispiele:**
- Datum-Formatierung
- Validierungs-Funktionen
- Konstanten
- Formatierungs-Funktionen

**Beispiel:**
```javascript
// utils/dateUtils.js
export const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('de-CH');
};

export const formatDateTime = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleString('de-CH');
};
```

---

### 6. Context (Optional)

**Zweck:** Global State Management (falls nötig)

**Verwendung:**
- Nur wenn Props-Drilling ein Problem wird
- Für wirklich globalen State (z.B. User-Authentifizierung)
- Sonst: Local State oder Custom Hooks bevorzugen

---

## Routing-Struktur

```javascript
// App.js
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage/HomePage';
import PersonDetailPage from './pages/PersonDetailPage/PersonDetailPage';
import NotFoundPage from './pages/NotFoundPage/NotFoundPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/person/:id" element={<PersonDetailPage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
```

---

## Migrationsschritte

### Schritt 1: Ordnerstruktur erstellen
```bash
mkdir -p frontend/src/{components/{common,person,kriterium},pages,services,hooks,utils,context,assets/{images,fonts}}
```

### Schritt 2: Services erstellen
- `api.js` - Axios-Instanz
- `personService.js` - Person-API
- `kriteriumService.js` - Kriterium-API

### Schritt 3: Komponenten organisieren
- Bestehende Komponenten in entsprechende Ordner verschieben
- Neue Komponenten-Struktur erstellen

### Schritt 4: Pages erstellen
- `HomePage.js`
- `PersonDetailPage.js`
- Routing in `App.js` einrichten

### Schritt 5: Custom Hooks erstellen
- `usePersonen.js`
- `usePerson.js`
- `useKriterien.js`

### Schritt 6: Utils erstellen
- `dateUtils.js`
- `validation.js`
- `constants.js`

### Schritt 7: Imports aktualisieren
- Alle Imports an neue Struktur anpassen
- Relative Pfade verwenden

### Schritt 8: Tests aktualisieren
- Test-Imports anpassen
- Neue Test-Struktur

---

## Vorteile der neuen Struktur

✅ **Organisation:** Klare Struktur, einfacher zu navigieren
✅ **Wiederverwendbarkeit:** Komponenten können einfach wiederverwendet werden
✅ **Wartbarkeit:** Änderungen sind lokalisiert
✅ **Testbarkeit:** Komponenten können isoliert getestet werden
✅ **Skalierbarkeit:** Einfach neue Features hinzufügen
✅ **Best Practices:** Folgt React Best Practices

---

## Wichtige Hinweise

⚠️ **Funktionalität erhalten:**
- Alle bestehenden Imports aktualisieren
- Routing funktioniert weiterhin
- API-Calls funktionieren weiterhin
- Tests müssen weiterhin funktionieren

⚠️ **Schrittweise Migration:**
- Nicht alles auf einmal ändern
- Schritt für Schritt migrieren
- Nach jedem Schritt testen

⚠️ **Import-Pfade:**
- Relative Pfade: `../../components/...`
- Oder absolute Pfade mit `jsconfig.json`:
```json
{
  "compilerOptions": {
    "baseUrl": "src"
  }
}
```

