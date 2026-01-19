# 🗂 Arbeitspakete & Aufgabenplanung

## Team

- **Mustafa**: Backend, CI/CD, Architektur
- **Lion**: Frontend, Testing (Unit & E2E)
- **Cedric**: DevOps, Dokumentation, Branch Protection

---

## User Stories (Auszug)

1. **US-001 – Person erfassen**
   - Als Kandidat möchte ich meine Personendaten (Name, Vorname, Thema, Abgabedatum) erfassen können, damit mein Projekt korrekt zugeordnet ist.
2. **US-002 – Kriterien abhaken**
   - Als Kandidat möchte ich pro Kriterium Anforderungen abhaken und Notizen erfassen, damit ich meinen Fortschritt dokumentieren kann.
3. **US-003 – Mutmassliche Note berechnen**
   - Als Kandidat möchte ich die berechneten Gütestufen und mutmasslichen Noten für Teil 1 und Teil 2 sehen, damit ich meinen Stand einschätzen kann.
4. **US-004 – CI/CD & Qualität**
   - Als Team möchte ich, dass bei jedem Commit automatisch gebaut, getestet und die Qualität geprüft wird, damit der Code stabil bleibt.

---

## Arbeitspakete nach Modulen

### Modul 324 – DevOps

| ID | Arbeitspaket | Beschreibung | Verantwortlich | Branch | Status |
|----|--------------|-------------|----------------|--------|--------|
| 324-AP1 | CI-Basis | GitHub Actions Workflow anlegen, Java/Node Setup | Cedric | `ci/setup-ci` | ✅ |
| 324-AP2 | Backend Pipeline | Backend-Build, Tests, Coverage, Artefakte | Mustafa | `feature/backend-ci` | ✅ |
| 324-AP3 | Frontend Pipeline | Frontend-Build & Tests, npm caching | Lion | `feature/frontend-ci` | ✅ |
| 324-AP4 | Code Quality | Checkstyle integrieren, Reports archivieren | Cedric | `feature/code-quality` | ✅ |
| 324-AP5 | Staging Deployment | Deployment auf `gh-pages` (Staging) | Cedric | `feature/staging-deploy` | ✅ |
| 324-AP6 | Branch Protection | Branch Protection Rules konfigurieren & dokumentieren | Cedric | `docs/branch-protection` | ✅ |

### Modul 450 – Testing

| ID | Arbeitspaket | Beschreibung | Verantwortlich | Branch | Status |
|----|--------------|-------------|----------------|--------|--------|
| 450-AP1 | Backend Unit-Tests | Tests für Services (Gütestufe, Progress, Loader) | Mustafa | `test/backend-services` | ✅ |
| 450-AP2 | Backend Integrationstests | REST-Controller mit MockMvc testen | Mustafa | `test/backend-integration` | ✅ |
| 450-AP3 | Frontend Unit-Tests | Jest/RTL-Tests für PersonForm, CriterionCard, GradesDisplay | Lion | `test/frontend-components` | ✅ |
| 450-AP4 | Cypress E2E | End-to-End Tests für Person-Flow | Lion | `test/e2e-person-flow` | ✅ |
| 450-AP5 | Testkonzept & Ergebnisse | TESTKONZEPT.md, TESTERGEBNISSE.md, Traceability Matrix | Cedric | `docs/testing` | ✅ |

---

## Aufgabenliste nach Sprints

### Sprint 1 – Basis & Architektur

| Aufgabe | Owner | Ergebnis |
|---------|-------|----------|
| Projektstruktur anlegen (frontend/backend) | Mustafa | Monorepo mit React + Spring Boot steht |
| Kriterien-JSON vorbereiten | Mustafa | `criteria.json` im Backend |
| Grundrouten im Frontend (Home, Person-Detail) | Lion | `App.js` mit Routing fertig |
| Grobes Datenmodell und ER-Skizze | Cedric | In PROJEKTDOKUMENTATION beschrieben |

### Sprint 2 – Funktionalität & Tests

| Aufgabe | Owner | Branch | Ergebnis |
|---------|-------|--------|----------|
| Person CRUD im Backend | Mustafa | `feature/person-api` | REST-API implementiert + Integrationstests |
| Kriterien-Fortschritt & Gütestufe | Mustafa | `feature/criterion-progress` | Service + Tests |
| PersonForm UI & Validierung | Lion | `feature/person-form-ui` | Formular inkl. Fehlermeldungen |
| CriterionCard & CriterionList | Lion | `feature/criteria-ui` | UI für Kriterien inkl. Checkboxen & Notizen |
| Testkonzept initial erstellen | Cedric | `docs/testing` | TESTKONZEPT.md erstellt |

### Sprint 3 – CI/CD & Qualitätsabsicherung

| Aufgabe | Owner | Branch | Ergebnis |
|---------|-------|--------|----------|
| CI-Pipeline für Backend | Mustafa | `feature/backend-ci` | Build, Tests, Coverage in CI |
| CI-Pipeline für Frontend | Lion | `feature/frontend-ci` | npm ci, Jest-Tests, Build in CI |
| Checkstyle integrieren | Cedric | `feature/code-quality` | Linter-Job in `ci.yml` |
| Branch Protection dokumentieren & aktivieren | Cedric | `docs/branch-protection` | BRANCH_PROTECTION.md + GitHub-Einstellungen |
| Testergebnisse & Traceability Matrix | Cedric | `docs/testing` | TESTERGEBNISSE.md + TRACEABILITY_MATRIX.md |

### Sprint 4 – Feinschliff & Bewertung

| Aufgabe | Owner | Ergebnis |
|---------|-------|----------|
| Frontend-Tests stabilisieren (Selector-Fixes, jest-dom) | Lion | Alle 18 Tests grün |
| Legacy-Code entfernen & Jacoco stabilisieren | Mustafa | Alte Services entfernt, Coverage stabil |
| Staging-Deployment via GitHub Pages | Cedric | `deploy-staging` Job in CI konfiguriert |
| Finale Bewertung & Setup-Anleitung | Cedric | FINALE_BEWERTUNG.md, SETUP_ANLEITUNG.md |

---

## Nachweis „Branches vor Merge getestet“

- Alle Feature-Branches werden über Pull Requests nach `develop` oder `main` gemergt.
- Branch Protection Regeln:
  - **Required Status Checks:** `backend-build-and-test`, `code-quality`, `pipeline-status`.
  - Merge ist nur möglich, wenn alle Checks ✅ sind.
- Typischer Ablauf:
  1. Arbeitspaket auswählen und Feature-Branch nach Namenskonvention erstellen (z.B. `feature/person-form-ui`).
  2. Commits mit sprechenden Messages (z.B. `feat: PersonForm Validierung hinzugefügt`).
  3. Push → GitHub Actions CI läuft automatisch.
  4. Pull Request eröffnen, CI-Status prüfen.
  5. Nach grünem Status + Review → Merge in `develop` / `main`.

Damit ist der Nachweis für **systematische Git-Nutzung** und **Tests vor Merge** durch Workflow, Branch Protection und CI-History gegeben.


