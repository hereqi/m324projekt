# Testkonzept

## Übersicht

Dieses Dokument beschreibt das Testkonzept für die Kriterien-Tracking Webapplikation.

**Ziel:** Mindestens 80% Code-Coverage für Backend und Frontend

---

## Testarten

### 1. Unit-Tests

**Zweck:** Testen einzelner Komponenten/Methoden in Isolation

#### Backend (Java/JUnit)
- **Framework:** JUnit 5, Mockito
- **Ziel:** Services, Utilities, Helper-Methoden
- **Coverage-Ziel:** >80%
- **Beispiele:**
  - `NotenBerechnungService` - Berechnungslogik testen
  - `KriterienLoaderService` - JSON-Parsing testen
  - Validierungslogik

#### Frontend (React/Jest)
- **Framework:** Jest, React Testing Library
- **Ziel:** Komponenten, Hooks, Utilities
- **Coverage-Ziel:** >80%
- **Beispiele:**
  - Komponenten-Rendering
  - User-Interaktionen (Klicks, Eingaben)
  - Custom Hooks

---

### 2. Integration-Tests

**Zweck:** Testen der Zusammenarbeit mehrerer Komponenten

#### Backend
- **Framework:** Spring Boot Test, Testcontainers (optional)
- **Ziel:** Controller-Endpoints, Service-Repository-Interaktion
- **Datenbank:** H2 in-memory für Tests
- **Beispiele:**
  - REST-API Endpoints
  - Datenbank-Operationen
  - Service-Repository-Integration

#### Frontend
- **Framework:** Jest, React Testing Library
- **Ziel:** Komponenten-Integration, API-Service-Mocking
- **Beispiele:**
  - Formular-Submit-Flows
  - API-Service-Integration (gemockt)
  - Routing-Integration

---

### 3. End-to-End (E2E) Tests (Optional)

**Zweck:** Testen kompletter User-Flows

- **Framework:** Cypress oder Playwright
- **Ziel:** Kritische User-Journeys
- **Beispiele:**
  - Person erstellen → Kriterien abhaken → Noten anzeigen
  - Person bearbeiten → Änderungen speichern
  - Fehlerbehandlung bei API-Fehlern

---

## Testfälle

### Backend Testfälle

#### Person Controller
**Test:** `PersonControllerTest.java`

| Testfall | Vorbedingung | Eingabe | Erwartetes Ergebnis |
|----------|-------------|---------|---------------------|
| GET /api/personen | Keine | - | Liste aller Personen (200) |
| GET /api/personen/{id} | Person existiert | ID: "1" | Person-Objekt (200) |
| GET /api/personen/{id} | Person existiert nicht | ID: "999" | 404 Not Found |
| POST /api/personen | Keine | Gültige Person-Daten | Person erstellt (201) |
| POST /api/personen | Keine | Ungültige Daten | 400 Bad Request |
| PUT /api/personen/{id} | Person existiert | ID + aktualisierte Daten | Person aktualisiert (200) |
| DELETE /api/personen/{id} | Person existiert | ID: "1" | Person gelöscht (204) |

#### Kriterien Controller
**Test:** `KriteriumControllerTest.java`

| Testfall | Vorbedingung | Eingabe | Erwartetes Ergebnis |
|----------|-------------|---------|---------------------|
| GET /api/kriterien | Kriterien in DB | - | Liste aller Kriterien (200) |
| GET /api/kriterien/{id} | Kriterium existiert | ID: "A01" | Kriterium-Objekt (200) |
| GET /api/kriterien/{id} | Kriterium existiert nicht | ID: "XXX" | 404 Not Found |

#### Noten-Berechnung Service
**Test:** `NotenBerechnungServiceTest.java`

| Testfall | Vorbedingung | Eingabe | Erwartetes Ergebnis |
|----------|-------------|---------|---------------------|
| Gütestufe 0 | Keine Anforderungen erfüllt | 0/5 Anforderungen | Gütestufe: 0 |
| Gütestufe 1 | 1-2 Anforderungen erfüllt | 2/5 Anforderungen | Gütestufe: 1 |
| Gütestufe 2 | 3-4 Anforderungen erfüllt | 3/5 Anforderungen | Gütestufe: 2 |
| Gütestufe 3 | Alle Anforderungen erfüllt | 5/5 Anforderungen | Gütestufe: 3 |
| Note Teil 1 | Gütestufen berechnet | Gütestufen-Array | Note zwischen 1.0-6.0 |
| Note Teil 2 | Gütestufen berechnet | Gütestufen-Array | Note zwischen 1.0-6.0 |

---

### Frontend Testfälle

#### Person-Liste Komponente
**Test:** `PersonList.test.js`

| Testfall | Vorbedingung | Eingabe | Erwartetes Ergebnis |
|----------|-------------|---------|---------------------|
| Rendering | Keine | - | Liste wird angezeigt |
| Loading State | API lädt | - | Loading-Spinner angezeigt |
| Error State | API-Fehler | - | Fehlermeldung angezeigt |
| Person klicken | Person in Liste | Klick auf Person | Navigation zu Detail-Seite |

#### Person-Formular Komponente
**Test:** `PersonForm.test.js`

| Testfall | Vorbedingung | Eingabe | Erwartetes Ergebnis |
|----------|-------------|---------|---------------------|
| Formular rendern | Keine | - | Alle Felder sichtbar |
| Validierung | Leeres Formular | Submit ohne Daten | Fehlermeldungen |
| Submit erfolgreich | Gültige Daten | Ausgefülltes Formular | Person erstellt, Erfolgsmeldung |
| Submit Fehler | API-Fehler | Gültige Daten | Fehlermeldung angezeigt |

---

## Test-Ausführung

### Backend Tests
```bash
cd backend
./mvnw test                    # Alle Tests
./mvnw test -Dtest=PersonControllerTest  # Spezifischer Test
./mvnw jacoco:report          # Coverage-Report generieren
```

### Frontend Tests
```bash
cd frontend
npm test                      # Alle Tests (Watch-Mode)
npm test -- --coverage        # Mit Coverage-Report
npm test -- --watchAll=false  # Einmalig ausführen
```

---

## CI/CD Integration

### GitHub Actions
- Tests werden bei jedem Commit/Pull Request ausgeführt
- Coverage wird geprüft (Minimum 80%)
- Fehlgeschlagene Tests blockieren Merge

### Coverage-Reports
- **Backend:** JaCoCo Report in `backend/target/site/jacoco/`
- **Frontend:** Coverage-Report in `frontend/coverage/`

---

## Best Practices

1. **AAA-Pattern:** Arrange, Act, Assert
2. **Isolation:** Tests sollten unabhängig voneinander sein
3. **Mocking:** Externe Abhängigkeiten mocken
4. **Naming:** Test-Namen sollten beschreibend sein
5. **Edge Cases:** Auch Grenzfälle testen
6. **Cleanup:** Test-Daten nach Tests aufräumen

---

## Test-Daten

### Backend
- H2 in-memory Datenbank für Tests
- Test-Fixtures in `src/test/resources/`
- @Sql Annotation für Test-Daten

### Frontend
- Mock-Daten in `src/__mocks__/` oder Test-Dateien
- MSW (Mock Service Worker) für API-Mocking (optional)

---

## Nächste Schritte

- [ ] Unit-Tests für alle Services implementieren
- [ ] Integration-Tests für alle Controller implementieren
- [ ] Frontend-Komponenten-Tests implementieren
- [ ] E2E-Tests für kritische Flows (optional)
- [ ] Coverage auf 80%+ bringen
- [ ] CI/CD Pipeline mit Tests konfigurieren

