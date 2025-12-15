# GitHub Issues für Kanban Board

Diese Datei enthält alle Issues, die für das Projekt erstellt werden sollten.
Kopiere die Issues in GitHub: Repository → Issues → New Issue

---

## 🎯 Epic 1: Datenbank & Backend Setup

### Issue #1: Datenbank-Modelle erstellen
**Labels:** `backend`, `database`, `high-priority`
**Milestone:** Sprint 1

**Beschreibung:**
Erstelle die JPA-Entitäten für:
- `Person` (id, name, vorname, thema, abgabedatum)
- `Kriterium` (id, titel, anforderungen[], gütestufen)
- `KriteriumErfüllung` (personId, kriteriumId, erfüllteAnforderungen[], notizen)

**Akzeptanzkriterien:**
- [ ] Alle Modelle mit JPA-Annotationen
- [ ] Relationships korrekt definiert
- [ ] Repository-Interfaces erstellt
- [ ] Basis-Tests für Modelle

---

### Issue #2: Kriterien JSON-Datei erstellen (3 Kriterien)
**Labels:** `backend`, `data`, `high-priority`
**Milestone:** Sprint 1

**Beschreibung:**
Konvertiere 3 Kriterien aus dem PDF in JSON-Format:
- 1x aus Kategorie A (Begleiten von ICT-Projekten)
- 1x aus Kategorie B/C/G oder H
- 1x aus Dokumentation

**Akzeptanzkriterien:**
- [ ] JSON-Datei mit korrekter Struktur
- [ ] Alle Felder vorhanden: ID, Titel, Anforderungen, Gütestufen
- [ ] Datei im Backend resources Ordner
- [ ] Beispiel-Kriterium C02 (Datenmodell entwickeln) inkludiert

---

### Issue #3: Kriterien-Loader Service erstellen
**Labels:** `backend`, `service`
**Milestone:** Sprint 1

**Beschreibung:**
Service zum Laden der Kriterien aus JSON-Datei beim Start der Anwendung.

**Akzeptanzkriterien:**
- [ ] Service liest JSON-Datei
- [ ] Kriterien werden in Datenbank gespeichert
- [ ] Fehlerbehandlung bei ungültiger JSON
- [ ] Unit-Test für Service

---

## 🎯 Epic 2: Backend API

### Issue #4: Person Controller & Service
**Labels:** `backend`, `api`, `high-priority`
**Milestone:** Sprint 1

**Beschreibung:**
REST-Controller für Person-Verwaltung:
- `GET /api/personen` - Alle Personen
- `GET /api/personen/{id}` - Person by ID
- `POST /api/personen` - Neue Person erstellen
- `PUT /api/personen/{id}` - Person aktualisieren
- `DELETE /api/personen/{id}` - Person löschen

**Akzeptanzkriterien:**
- [ ] Alle Endpoints implementiert
- [ ] Validierung der Eingaben
- [ ] Fehlerbehandlung
- [ ] Integration-Tests

---

### Issue #5: Kriterien Controller
**Labels:** `backend`, `api`
**Milestone:** Sprint 1

**Beschreibung:**
REST-Controller für Kriterien:
- `GET /api/kriterien` - Alle Kriterien abrufen

**Akzeptanzkriterien:**
- [ ] Endpoint implementiert
- [ ] Kriterien werden aus DB geladen
- [ ] Unit-Test

---

### Issue #6: Kriterium-Erfüllung Controller
**Labels:** `backend`, `api`, `high-priority`
**Milestone:** Sprint 2

**Beschreibung:**
REST-Controller für Kriterium-Erfüllung:
- `GET /api/personen/{personId}/erfuellungen` - Alle Erfüllungen einer Person
- `PUT /api/kriterien/{kriteriumId}/anforderungen` - Anforderungen aktualisieren (mit personId im Body)

**Akzeptanzkriterien:**
- [ ] Endpoints implementiert
- [ ] Anforderungen können gespeichert werden
- [ ] Notizen können gespeichert werden
- [ ] Integration-Tests

---

### Issue #7: Noten-Berechnung Service
**Labels:** `backend`, `service`, `high-priority`
**Milestone:** Sprint 2

**Beschreibung:**
Service zur Berechnung der Gütestufen und Noten:
- Berechne Gütestufe (0-3) pro Kriterium basierend auf erfüllten Anforderungen
- Berechne mutmassliche Note für Teil 1 und Teil 2

**Akzeptanzkriterien:**
- [ ] Gütestufe-Berechnung korrekt
- [ ] Note-Berechnung für Teil 1 und Teil 2
- [ ] Unit-Tests mit verschiedenen Szenarien
- [ ] Edge Cases behandelt

---

### Issue #8: Noten Controller
**Labels:** `backend`, `api`
**Milestone:** Sprint 2

**Beschreibung:**
REST-Controller für Noten:
- `GET /api/noten/{personId}` - Berechnete Noten abrufen

**Akzeptanzkriterien:**
- [ ] Endpoint gibt Gütestufen pro Kriterium zurück
- [ ] Endpoint gibt Note für Teil 1 und Teil 2 zurück
- [ ] Integration-Test

---

## 🎯 Epic 3: Frontend

### Issue #9: Frontend Routing Setup
**Labels:** `frontend`, `setup`
**Milestone:** Sprint 1

**Beschreibung:**
React Router einrichten mit:
- `/` - Personen-Übersicht
- `/person/:id` - Person-Detail

**Akzeptanzkriterien:**
- [ ] React Router installiert
- [ ] Routes definiert
- [ ] Navigation funktioniert

---

### Issue #10: Person-Liste Komponente
**Labels:** `frontend`, `component`, `high-priority`
**Milestone:** Sprint 1

**Beschreibung:**
Komponente zur Anzeige aller Personen:
- Liste aller Personen
- Button "Neue Person"
- Klick auf Person → Detail-Seite

**Akzeptanzkriterien:**
- [ ] Personen werden vom Backend geladen
- [ ] Liste wird angezeigt
- [ ] Navigation zu Detail funktioniert
- [ ] Loading-State
- [ ] Error-Handling

---

### Issue #11: Person-Formular Komponente
**Labels:** `frontend`, `component`, `high-priority`
**Milestone:** Sprint 1

**Beschreibung:**
Formular zum Erstellen/Bearbeiten von Personen:
- Name, Vorname, Thema, Abgabedatum
- Validierung
- Submit zum Backend

**Akzeptanzkriterien:**
- [ ] Alle Felder vorhanden
- [ ] Validierung funktioniert
- [ ] Submit erstellt/aktualisiert Person
- [ ] Erfolgsmeldung
- [ ] Fehlerbehandlung

---

### Issue #12: Person-Detail Komponente
**Labels:** `frontend`, `component`, `high-priority`
**Milestone:** Sprint 2

**Beschreibung:**
Detail-Seite für eine Person:
- Person-Informationen anzeigen
- Kriterien-Liste anzeigen
- Anforderungen abhaken
- Notizen eingeben

**Akzeptanzkriterien:**
- [ ] Person-Daten werden angezeigt
- [ ] Kriterien werden geladen
- [ ] Checkboxes für Anforderungen
- [ ] Notizen-Textfeld
- [ ] Speichern funktioniert

---

### Issue #13: Kriterium-Komponente
**Labels:** `frontend`, `component`
**Milestone:** Sprint 2

**Beschreibung:**
Wiederverwendbare Komponente für ein einzelnes Kriterium:
- Titel und ID anzeigen
- Liste der Anforderungen mit Checkboxes
- Notizen-Textfeld
- Gütestufe anzeigen

**Akzeptanzkriterien:**
- [ ] Komponente ist wiederverwendbar
- [ ] Checkbox-State wird gespeichert
- [ ] Notizen werden gespeichert
- [ ] Gütestufe wird angezeigt

---

### Issue #14: Noten-Anzeige Komponente
**Labels:** `frontend`, `component`
**Milestone:** Sprint 2

**Beschreibung:**
Komponente zur Anzeige der berechneten Noten:
- Gütestufe pro Kriterium
- Mutmassliche Note für Teil 1
- Mutmassliche Note für Teil 2

**Akzeptanzkriterien:**
- [ ] Noten werden vom Backend geladen
- [ ] Übersichtlich dargestellt
- [ ] Gütestufen pro Kriterium sichtbar
- [ ] Automatische Aktualisierung bei Änderungen

---

## 🎯 Epic 4: Testing

### Issue #15: Testkonzept erstellen
**Labels:** `testing`, `documentation`
**Milestone:** Sprint 1

**Beschreibung:**
Dokumentiere das Testkonzept:
- Testarten (Unit, Integration, E2E)
- Testframework (Jest für Frontend, JUnit für Backend)
- Testabdeckungs-Ziel: 80%

**Akzeptanzkriterien:**
- [ ] Dokument erstellt
- [ ] Alle Testarten beschrieben
- [ ] Testfälle dokumentiert (Vorbedingungen, Eingaben, erwartete Ergebnisse)

---

### Issue #16: Backend Unit-Tests
**Labels:** `testing`, `backend`
**Milestone:** Sprint 2

**Beschreibung:**
Unit-Tests für Backend-Services:
- NotenBerechnungService Tests
- KriterienLoaderService Tests
- Alle Services mit >80% Coverage

**Akzeptanzkriterien:**
- [ ] Tests für alle Services
- [ ] Mocking für Abhängigkeiten
- [ ] Coverage >80%
- [ ] Tests laufen in CI/CD

---

### Issue #17: Backend Integration-Tests
**Labels:** `testing`, `backend`
**Milestone:** Sprint 2

**Beschreibung:**
Integration-Tests für REST-Controller:
- Alle Controller-Endpoints testen
- Test-Datenbank verwenden (H2)
- Mocking für externe Abhängigkeiten

**Akzeptanzkriterien:**
- [ ] Tests für alle Endpoints
- [ ] H2 Test-Datenbank
- [ ] Tests laufen in CI/CD

---

### Issue #18: Frontend Unit-Tests
**Labels:** `testing`, `frontend`
**Milestone:** Sprint 2

**Beschreibung:**
Unit-Tests für React-Komponenten:
- Alle Komponenten testen
- Testing Library verwenden
- >80% Coverage

**Akzeptanzkriterien:**
- [ ] Tests für alle Komponenten
- [ ] User-Interaktionen getestet
- [ ] Coverage >80%
- [ ] Tests laufen in CI/CD

---

### Issue #19: E2E Tests (optional)
**Labels:** `testing`, `e2e`
**Milestone:** Sprint 3

**Beschreibung:**
End-to-End Tests mit Cypress oder Playwright:
- Kritische User-Flows testen
- Person erstellen → Kriterien abhaken → Noten anzeigen

**Akzeptanzkriterien:**
- [ ] E2E Framework eingerichtet
- [ ] Mindestens 3 kritische Flows getestet
- [ ] Tests laufen in CI/CD

---

## 🎯 Epic 5: DevOps / CI/CD

### Issue #20: GitHub Actions Pipeline - Build
**Labels:** `devops`, `ci-cd`, `high-priority`
**Milestone:** Sprint 2

**Beschreibung:**
GitHub Actions Pipeline erstellen:
- Backend Build bei jedem Commit
- Frontend Build bei jedem Commit
- Fehlgeschlagene Builds stoppen Pipeline

**Akzeptanzkriterien:**
- [ ] Pipeline-Datei erstellt
- [ ] Backend wird gebaut
- [ ] Frontend wird gebaut
- [ ] Fehler stoppen Pipeline
- [ ] Alle Schritte kommentiert

---

### Issue #21: GitHub Actions Pipeline - Tests
**Labels:** `devops`, `ci-cd`, `high-priority`
**Milestone:** Sprint 2

**Beschreibung:**
Tests in Pipeline integrieren:
- Unit-Tests ausführen
- Integration-Tests ausführen
- Mindestens 80% müssen bestehen
- Testergebnisse in GitHub Actions anzeigen

**Akzeptanzkriterien:**
- [ ] Tests werden automatisch ausgeführt
- [ ] Coverage wird geprüft (80% Minimum)
- [ ] Ergebnisse werden angezeigt
- [ ] Fehlgeschlagene Tests stoppen Pipeline

---

### Issue #22: Linter Integration
**Labels:** `devops`, `code-quality`
**Milestone:** Sprint 2

**Beschreibung:**
Linter in Pipeline integrieren:
- ESLint für Frontend
- Checkstyle/SpotBugs für Backend
- Fehler werden gemeldet

**Akzeptanzkriterien:**
- [ ] Linter konfiguriert
- [ ] Läuft in Pipeline
- [ ] Fehler werden gemeldet
- [ ] Fehlerhafter Code wird nicht gemerged

---

### Issue #23: Staging Deployment
**Labels:** `devops`, `deployment`
**Milestone:** Sprint 3

**Beschreibung:**
Automatisches Deployment in Staging:
- Nach erfolgreichen Tests
- Docker Images bauen
- In Staging-Umgebung deployen

**Akzeptanzkriterien:**
- [ ] Deployment nach erfolgreichen Tests
- [ ] Docker Images werden gebaut
- [ ] Staging-Umgebung wird aktualisiert
- [ ] GitHub Secrets für sensible Daten

---

### Issue #24: Pipeline Dokumentation
**Labels:** `devops`, `documentation`
**Milestone:** Sprint 3

**Beschreibung:**
Dokumentiere die Pipeline:
- Funktionsweise beschreiben
- Schritte erklären
- Fehlermeldungen dokumentieren

**Akzeptanzkriterien:**
- [ ] Dokumentation erstellt
- [ ] Alle Schritte erklärt
- [ ] Fehlermeldungen dokumentiert

---

## 🎯 Epic 6: Dokumentation & KI

### Issue #25: KI-Nutzung dokumentieren
**Labels:** `documentation`, `ki`
**Milestone:** Sprint 3

**Beschreibung:**
Dokumentiere alle KI-Nutzungen:
- Was wurde mit KI erstellt?
- Wo im Code?
- Kurze Notiz pro Stelle

**Akzeptanzkriterien:**
- [ ] Dokument erstellt (KI_USAGE.md)
- [ ] Alle KI-Nutzungen dokumentiert
- [ ] Code-Stellen referenziert
- [ ] Keine Prompts dokumentiert

---

### Issue #26: README aktualisieren
**Labels:** `documentation`
**Milestone:** Sprint 3

**Beschreibung:**
README mit vollständiger Dokumentation:
- Setup-Anleitung
- API-Dokumentation
- Test-Anleitung
- Deployment-Anleitung

**Akzeptanzkriterien:**
- [ ] README vollständig
- [ ] Alle Anleitungen vorhanden
- [ ] Code-Beispiele
- [ ] Screenshots (optional)

---

## 📋 Priorisierung

### Sprint 1 (Höchste Priorität):
1. Issue #2: Kriterien JSON erstellen
2. Issue #1: Datenbank-Modelle
3. Issue #3: Kriterien-Loader
4. Issue #4: Person Controller
5. Issue #9: Frontend Routing
6. Issue #10: Person-Liste
7. Issue #11: Person-Formular
8. Issue #15: Testkonzept

### Sprint 2:
1. Issue #5: Kriterien Controller
2. Issue #6: Kriterium-Erfüllung Controller
3. Issue #7: Noten-Berechnung
4. Issue #8: Noten Controller
5. Issue #12: Person-Detail
6. Issue #13: Kriterium-Komponente
7. Issue #14: Noten-Anzeige
8. Issue #16-18: Tests
9. Issue #20-22: CI/CD Pipeline

### Sprint 3:
1. Issue #19: E2E Tests
2. Issue #23: Staging Deployment
3. Issue #24: Pipeline Dokumentation
4. Issue #25: KI-Dokumentation
5. Issue #26: README

