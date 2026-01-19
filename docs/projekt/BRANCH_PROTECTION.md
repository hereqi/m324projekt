# Branch Protection Rules - Anleitung

## Übersicht

Diese Anleitung beschreibt, wie Branch Protection Rules in GitHub konfiguriert werden, um sicherzustellen, dass Branches vor dem Merge getestet sind.

**Anforderung:** Branches müssen vor dem Merge getestet sein (mit Nachweis)

---

## Schritt-für-Schritt Anleitung

### 1. Repository-Einstellungen öffnen

1. Navigiere zu deinem GitHub Repository
2. Klicke auf **Settings** (Einstellungen)
3. Wähle **Branches** im linken Menü

### 2. Branch Protection Rule hinzufügen

1. Klicke auf **Add branch protection rule** (oder **Add rule**)
2. Gib den Branch-Namen ein:
   - Für main: `main`
   - Für develop: `develop`
   - Für alle Feature-Branches: `feature/*`

### 3. Empfohlene Einstellungen für `main`

Aktiviere folgende Optionen:

#### ✅ Require a pull request before merging
- **Require approvals:** 1 (mindestens ein Review erforderlich)
- **Dismiss stale pull request approvals when new commits are pushed:** ✅
- **Require review from Code Owners:** Optional

#### ✅ Require status checks to pass before merging
- **Require branches to be up to date before merging:** ✅
- **Status checks that are required:**
  - `Backend Build & Test` (Job-Name aus ci.yml)
  - `Code Quality` (Job-Name aus ci.yml)
  - `Pipeline Status` (Job-Name aus ci.yml)

#### ✅ Require conversation resolution before merging
- Alle Review-Kommentare müssen beantwortet werden

#### ✅ Include administrators
- Regeln gelten auch für Administratoren

#### ❌ Allow force pushes
- Sollte DEAKTIVIERT bleiben

#### ❌ Allow deletions
- Sollte DEAKTIVIERT bleiben

### 4. Einstellungen speichern

Klicke auf **Create** oder **Save changes**

---

## Konfiguration für verschiedene Branches

### Main Branch

```
Branch name pattern: main

✅ Require a pull request before merging
   - Required approvals: 1
   - Dismiss stale approvals: Yes
   
✅ Require status checks to pass
   - Required checks:
     - backend-build-and-test
     - code-quality
     - pipeline-status
   - Require up-to-date: Yes
   
✅ Require conversation resolution
✅ Include administrators
❌ Allow force pushes
❌ Allow deletions
```

### Develop Branch

```
Branch name pattern: develop

✅ Require a pull request before merging
   - Required approvals: 1
   
✅ Require status checks to pass
   - Required checks:
     - backend-build-and-test
   - Require up-to-date: Yes
```

---

## Workflow mit Branch Protection

### Entwickler-Workflow

```bash
# 1. Feature-Branch erstellen
git checkout develop
git pull origin develop
git checkout -b feature/mein-feature

# 2. Änderungen machen und committen
git add .
git commit -m "feat: Mein Feature implementiert"

# 3. Push zum Remote
git push -u origin feature/mein-feature

# 4. Pull Request erstellen (auf GitHub)
#    - Von: feature/mein-feature
#    - Nach: develop

# 5. CI/CD Pipeline läuft automatisch
#    - Tests müssen erfolgreich sein
#    - Code-Quality muss bestehen

# 6. Code Review anfordern
#    - Mindestens 1 Approval erforderlich

# 7. Nach Approval: Merge
```

### Was passiert bei einem Pull Request?

1. **Pipeline startet automatisch**
   - Backend Build & Tests
   - Code Quality Checks
   - Coverage-Prüfung

2. **Status-Checks werden angezeigt**
   - ✅ Grün = Erfolgreich
   - ❌ Rot = Fehlgeschlagen
   - 🟡 Gelb = In Bearbeitung

3. **Merge-Button**
   - Nur aktiv wenn alle Checks erfolgreich
   - Nur aktiv wenn Approvals vorhanden

---

## Nachweis der Branch Protection

### 1. Screenshots

Erstelle Screenshots von:
- Branch Protection Rule Konfiguration
- Pull Request mit Status-Checks
- Merge-Button (deaktiviert ohne Approvals)

### 2. Pull Request History

Die Pull Request History zeigt:
- Welche Checks durchgeführt wurden
- Wer den Review gemacht hat
- Wann gemergt wurde

### 3. GitHub Actions History

Die Actions-History zeigt:
- Alle Pipeline-Durchläufe
- Erfolgreiche/Fehlgeschlagene Builds
- Coverage-Berichte

---

## Beispiel: Branch Protection in Action

### Szenario: Feature-Branch mergen

1. **Entwickler erstellt PR**
   ```
   feature/kriterien-ui → develop
   ```

2. **Pipeline läuft**
   ```
   ✅ Backend Build & Test (2m 30s)
   ✅ Code Quality (45s)
   ✅ Pipeline Status (10s)
   ```

3. **Review angefordert**
   ```
   👤 @teammate wurde als Reviewer hinzugefügt
   ```

4. **Review abgeschlossen**
   ```
   ✅ @teammate approved this pull request
   ```

5. **Merge möglich**
   ```
   [Merge pull request] Button ist grün und klickbar
   ```

---

## Fehlerszenarien

### Tests fehlgeschlagen

```
❌ Backend Build & Test - Some tests failed
   - 2 tests failed: GuetestufeServiceTest.testXYZ
   
[Merge pull request] Button ist deaktiviert
"Required status check 'backend-build-and-test' is failing"
```

**Lösung:** Fehler beheben, neuen Commit pushen, Pipeline läuft erneut

### Kein Approval

```
⏳ Review required
   - 0 of 1 required approvals

[Merge pull request] Button ist deaktiviert
"At least 1 approving review is required"
```

**Lösung:** Review von Teammitglied anfordern und warten

### Branch nicht aktuell

```
⚠️ This branch is out-of-date with the base branch

[Update branch] Button erscheint
```

**Lösung:** Branch aktualisieren (Update branch klicken)

---

## Checkliste für Bewertung

✅ Branch Protection Rule für `main` konfiguriert  
✅ Required Status Checks aktiviert  
✅ Pull Request vor Merge erforderlich  
✅ Pipeline läuft automatisch bei PR  
✅ Merge nur bei erfolgreichen Tests möglich  
✅ Nachweis durch PR-History und Screenshots  

---

## Weiterführende Links

- [GitHub Docs: Branch Protection](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/defining-the-mergeability-of-pull-requests/about-protected-branches)
- [GitHub Docs: Required Status Checks](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/defining-the-mergeability-of-pull-requests/about-protected-branches#require-status-checks-before-merging)



