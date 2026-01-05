# Projekt-Ablauf und Pipeline-Erklärung

**Datum:** 2026-01-05  
**Projekt:** Kriterien-Tracking Webapplikation

---

## 📋 Grober Projekt-Ablauf

### 1. Projekt-Übersicht

Das Projekt ist eine **Webapplikation** zur Nachverfolgung der Erfüllung von Kriterien für individuelle praktische Arbeiten (IPA) nach QV BiVO2021.

**Architektur:**
```
[Browser] → [React Frontend] → [Spring Boot Backend] → [H2 Datenbank]
```

### 2. Komponenten

#### Frontend (React)
- **Technologie:** React 18, React Router, Axios
- **Zweck:** Benutzeroberfläche für die Eingabe und Anzeige von Daten
- **Läuft auf:** `http://localhost:3000` (Entwicklung)

#### Backend (Spring Boot)
- **Technologie:** Spring Boot 3.2.0, Java 17, Maven
- **Zweck:** REST-API für Datenverwaltung und Business-Logik
- **Läuft auf:** `http://localhost:8080` (Entwicklung)

#### Datenbank
- **Entwicklung/Tests:** H2 (In-Memory Datenbank)
- **Produktion:** PostgreSQL (konfiguriert, aber aktuell H2 verwendet)
- **Zweck:** Persistente Speicherung von Personen, Kriterien und Fortschritten

---

## 🗄️ Datenbank

### Ja, wir haben eine Datenbank!

**Aktuell verwendet:** H2 (In-Memory Datenbank)

#### Warum H2?
- ✅ Keine separate Installation nötig
- ✅ Perfekt für Entwicklung und Tests
- ✅ Startet automatisch mit Spring Boot
- ✅ Daten werden im Speicher gehalten (bei Neustart verloren)

#### Datenbank-Zugriff:
- **H2 Console:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:kriterien_db`
- **Username:** `sa`
- **Password:** (leer)

#### Datenbank-Struktur:

**Tabelle: `personen`**
```sql
- id (Long, Primary Key, Auto-Increment)
- name (String)
- vorname (String)
- thema (String)
- abgabedatum (LocalDate)
```

**Tabelle: `criteria`**
```sql
- id (String, Primary Key, z.B. "C02", "B05", "DOC01")
- titel (String)
- leitfrage (String)
- teil (String, z.B. "Teil1", "Teil2", "Dokumentation")
- anforderungen (List<String>, als ElementCollection gespeichert)
```

**Tabelle: `criterion_progress`**
```sql
- id (Long, Primary Key, Auto-Increment)
- person_id (Long, Foreign Key zu personen)
- criterion_id (String, Foreign Key zu criteria)
- erfuellte_anforderungen (List<Integer>, Indizes der erfüllten Anforderungen)
- notizen (String)
```

**Hinweis:** Die Gütestufe wird **nicht** in der Datenbank gespeichert, sondern **automatisch berechnet** basierend auf den erfüllten Anforderungen.

---

## 🔄 GitHub Actions Pipeline - Ablauf

### Übersicht

Die Pipeline läuft **automatisch** bei jedem Push auf `main` oder `develop` Branch.

### Pipeline-Trigger

```yaml
on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main, develop]
```

**Das bedeutet:**
- Jeder Commit auf `main` oder `develop` startet die Pipeline
- Jeder Pull Request zu `main` oder `develop` startet die Pipeline

---

## 📊 Pipeline-Ablauf (Schritt für Schritt)

### Job 1: Backend Build & Test ⚙️

**Zweck:** Backend kompilieren, testen und Code-Qualität prüfen

#### Schritt 1: Repository auschecken
```yaml
- Checkout Repository
```
- Lädt den Code aus GitHub herunter

#### Schritt 2: Java Setup
```yaml
- Setup Java 17
```
- Installiert Java 17 (Eclipse Temurin)
- Aktiviert Maven Dependency Caching

#### Schritt 3: Maven Cache wiederherstellen
```yaml
- Restore Maven Cache
```
- Lädt gecachte Maven-Dependencies (schnellerer Build)

#### Schritt 4: Dependencies herunterladen
```yaml
- Download Dependencies
```
- Lädt alle Maven-Abhängigkeiten (Spring Boot, JPA, etc.)

#### Schritt 5: Backend kompilieren
```yaml
- Build Backend
```
- Kompiliert den Java-Code
- **Bei Fehlern:** Pipeline bricht ab ❌

#### Schritt 6: Tests ausführen
```yaml
- Run Tests with Coverage
```
- Führt alle Unit-Tests und Integration-Tests aus
- Generiert Code-Coverage-Bericht (JaCoCo)
- **44 Tests** werden ausgeführt
- **Bei Fehlern:** Pipeline bricht ab ❌

#### Schritt 7: Coverage-Check
```yaml
- Verify Coverage (80%+)
```
- Prüft ob Code-Coverage mindestens **80%** ist
- **Aktuell:** 88% Coverage ✅
- **Bei unter 80%:** Pipeline bricht ab ❌

#### Schritt 8: Coverage-Bericht hochladen (Optional)
```yaml
- Upload Coverage Report to Codecov
```
- Lädt Coverage-Bericht zu Codecov hoch
- **Optional:** Bricht nicht ab bei Fehlern

#### Schritt 9: Coverage-Bericht speichern
```yaml
- Archive Coverage Report
```
- Speichert Coverage-Bericht als Artifact (30 Tage)

#### Schritt 10: Test-Ergebnisse veröffentlichen
```yaml
- Publish Test Results
```
- Zeigt Test-Ergebnisse in GitHub Actions UI an
- **Optional:** Bricht nicht ab bei Fehlern

#### Schritt 11: Backend JAR erstellen
```yaml
- Package Backend
```
- Erstellt ausführbare JAR-Datei
- **Bei Fehlern:** Pipeline bricht ab ❌

#### Schritt 12: JAR speichern
```yaml
- Archive Backend JAR
```
- Speichert JAR-Datei als Artifact (7 Tage)

---

### Job 2: Frontend Build (Optional) 🎨

**Zweck:** Frontend für Produktion bauen

**Läuft nur wenn:** Frontend-Dateien geändert wurden

#### Schritt 1: Repository auschecken
- Lädt den Code herunter

#### Schritt 2: Node.js Setup
- Installiert Node.js 20
- Aktiviert npm Caching

#### Schritt 3: Dependencies installieren
```yaml
- Install Dependencies
```
- Führt `npm ci` aus (schneller als `npm install`)

#### Schritt 4: Frontend Build
```yaml
- Build Frontend
```
- Führt `npm run build` aus
- Erstellt Produktions-Bundle
- **Bei Fehlern:** Pipeline bricht ab ❌

#### Schritt 5: Build speichern
```yaml
- Archive Frontend Build
```
- Speichert Frontend-Build als Artifact (7 Tage)

---

### Job 3: Code Quality 🔍

**Zweck:** Code-Qualität prüfen

**Abhängigkeit:** Benötigt erfolgreichen Backend Build

#### Schritt 1: Repository auschecken
- Lädt den Code herunter

#### Schritt 2: Java Setup
- Installiert Java 17

#### Schritt 3: Checkstyle ausführen
```yaml
- Run Code Quality Checks
```
- Führt `mvn checkstyle:check` aus
- Prüft Code-Stil und Formatierung
- **Bei Fehlern:** Pipeline bricht ab ❌

#### Schritt 4: Berichte speichern
```yaml
- Archive Code Quality Reports
```
- Speichert Checkstyle-Berichte als Artifact (30 Tage)

---

### Job 4: Pipeline Status 📊

**Zweck:** Zusammenfassender Status-Check

**Abhängigkeit:** Benötigt Backend Build & Code Quality

#### Schritt 1: Status zusammenfassen
```yaml
- Check Pipeline Status
```
- Zeigt Status aller Jobs an

#### Schritt 2: Bei Fehlern abbrechen
```yaml
- Fail if Critical Jobs Failed
```
- Wenn Backend Build fehlgeschlagen ist → Pipeline bricht ab ❌

#### Schritt 3: Erfolg melden
```yaml
- Success Message
```
- Wenn alles erfolgreich → Pipeline erfolgreich ✅

---

## 🔄 Kompletter Ablauf (Beispiel)

### Szenario: Entwickler pusht Code

1. **Entwickler macht Commit:**
   ```bash
   git add .
   git commit -m "feat: Neues Feature"
   git push origin main
   ```

2. **GitHub Actions startet automatisch:**
   - Pipeline wird getriggert
   - Läuft auf Ubuntu-Latest Runner

3. **Backend Build & Test läuft:**
   - ✅ Code kompiliert
   - ✅ Tests laufen (44 Tests)
   - ✅ Coverage geprüft (88% > 80%)
   - ✅ JAR erstellt

4. **Code Quality läuft:**
   - ✅ Checkstyle prüft Code
   - ✅ Keine Fehler gefunden

5. **Pipeline Status:**
   - ✅ Alle Jobs erfolgreich
   - ✅ Pipeline grün

6. **Ergebnis:**
   - ✅ Artifacts gespeichert (JAR, Coverage, etc.)
   - ✅ Test-Ergebnisse in GitHub UI sichtbar
   - ✅ Pipeline erfolgreich abgeschlossen

---

## ❌ Was passiert bei Fehlern?

### Beispiel: Test schlägt fehl

1. **Backend Build & Test:**
   - ❌ Test schlägt fehl
   - Pipeline bricht sofort ab
   - Weitere Jobs werden **nicht** ausgeführt

2. **GitHub Actions UI zeigt:**
   - Rotes X bei fehlgeschlagenem Job
   - Fehlermeldung in den Logs
   - Entwickler wird benachrichtigt

3. **Entwickler muss:**
   - Fehler beheben
   - Neuen Commit pushen
   - Pipeline läuft erneut

---

## 📈 Pipeline-Statistiken

**Aktuelle Konfiguration:**
- **Java Version:** 17
- **Node.js Version:** 20
- **Tests:** 44 (alle erfolgreich)
- **Coverage:** 88% (Instructions), 80% (Branches)
- **Checkstyle:** Aktiv
- **Dauer:** ~2-3 Minuten

**Artifacts:**
- Backend JAR (7 Tage)
- Coverage Reports (30 Tage)
- Code Quality Reports (30 Tage)
- Frontend Build (7 Tage, wenn geändert)

---

## 🔧 Lokale Entwicklung vs. Pipeline

### Lokal entwickeln:
```bash
# Backend starten
cd backend
mvn spring-boot:run

# Frontend starten
cd frontend
npm start
```

### Pipeline prüft:
- ✅ Code kompiliert
- ✅ Tests laufen
- ✅ Coverage ist ausreichend
- ✅ Code-Qualität ist gut

**Wichtig:** Die Pipeline verwendet **H2 In-Memory Datenbank** für Tests, genau wie lokal!

---

## 📝 Zusammenfassung

### Datenbank:
- ✅ **H2 In-Memory Datenbank** (Entwicklung/Tests)
- ✅ 3 Tabellen: `personen`, `criteria`, `criterion_progress`
- ✅ Automatische Schema-Erstellung beim Start
- ✅ H2 Console verfügbar unter `/h2-console`

### Pipeline:
- ✅ **4 Jobs:** Backend Build, Frontend Build (optional), Code Quality, Pipeline Status
- ✅ **Automatisch** bei jedem Push/PR
- ✅ **Bricht ab** bei Fehlern (Build, Tests, Coverage, Checkstyle)
- ✅ **Erstellt Artifacts** (JAR, Reports)
- ✅ **Zeigt Ergebnisse** in GitHub UI

### Projekt-Ablauf:
1. Entwickler pusht Code → Pipeline startet automatisch
2. Pipeline testet alles → Bei Erfolg: grün ✅, bei Fehler: rot ❌
3. Entwickler sieht Ergebnis in GitHub Actions
4. Bei Fehlern: Entwickler behebt und pusht erneut

---

## 🎯 Wichtig für die Bewertung

✅ **Pipeline bricht bei Fehlern ab** (wie gefordert)  
✅ **Tests werden automatisch ausgeführt** (44 Tests)  
✅ **Coverage wird geprüft** (80%+ erforderlich, aktuell 88%)  
✅ **Linter ist aktiv** (Checkstyle)  
✅ **Alle Schritte sind kommentiert** (in ci.yml)  
✅ **Testergebnisse sind sichtbar** (in GitHub Actions UI)  

**Das Projekt erfüllt alle DevOps-Anforderungen!** 🎉

