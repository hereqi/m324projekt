# Anleitung: GitHub Issues für Kanban Board erstellen

## Schritt 1: Labels erstellen

Gehe zu: `https://github.com/hereqi/m324projekt` → **Settings** → **Labels**

Erstelle folgende Labels:

### Prioritäten:
- `high-priority` (rot)
- `medium-priority` (orange)
- `low-priority` (gelb)

### Kategorien:
- `backend` (blau)
- `frontend` (grün)
- `database` (lila)
- `api` (türkis)
- `testing` (pink)
- `devops` (grau)
- `ci-cd` (dunkelblau)
- `documentation` (hellblau)
- `bug` (rot)
- `enhancement` (grün)
- `task` (grau)
- `ki` (orange)

## Schritt 2: Milestones erstellen

Gehe zu: **Issues** → **Milestones** → **New Milestone**

Erstelle:
- **Sprint 1** (Deadline: [Datum setzen])
- **Sprint 2** (Deadline: [Datum setzen])
- **Sprint 3** (Deadline: [Datum setzen])

## Schritt 3: Issues erstellen

Gehe zu: **Issues** → **New Issue**

### Option A: Manuell aus GITHUB_ISSUES.md kopieren

1. Öffne `GITHUB_ISSUES.md` im Repository
2. Kopiere den Inhalt eines Issues
3. Erstelle neues Issue auf GitHub
4. Füge Titel, Beschreibung und Akzeptanzkriterien ein
5. Wähle Labels und Milestone
6. Klicke auf **Submit new issue**

### Option B: Issue Templates verwenden

1. Gehe zu **Issues** → **New Issue**
2. Wähle ein Template (Feature, Bug, Task)
3. Fülle die Felder aus
4. Wähle Labels und Milestone
5. Klicke auf **Submit new issue**

## Schritt 4: Kanban Board einrichten

1. Gehe zu: **Projects** → **New Project**
2. Wähle **Board** als Template
3. Name: "M324 Projekt Kanban"
4. Klicke auf **Create**

### Spalten einrichten:
- **Backlog** (alle neuen Issues)
- **To Do** (Issues die als nächstes bearbeitet werden)
- **In Progress** (Issues in Bearbeitung)
- **Review** (Issues die Code Review benötigen)
- **Done** (abgeschlossene Issues)

### Issues zum Board hinzufügen:
1. Öffne ein Issue
2. Klicke rechts auf **Projects**
3. Wähle "M324 Projekt Kanban"
4. Wähle die Spalte (z.B. "To Do")

## Schritt 5: Automatisierung (Optional)

Gehe zu: **Settings** → **Projects** → **Workflows**

Aktiviere:
- Automatisch zu "To Do" wenn Issue erstellt wird
- Automatisch zu "In Progress" wenn Issue zugewiesen wird
- Automatisch zu "Done" wenn Issue geschlossen wird

## Schnellstart: Erste 5 Issues erstellen

Kopiere diese Issues aus `GITHUB_ISSUES.md`:

1. **Issue #2**: Kriterien JSON-Datei erstellen (3 Kriterien) - `high-priority`
2. **Issue #1**: Datenbank-Modelle erstellen - `high-priority`
3. **Issue #4**: Person Controller & Service - `high-priority`
4. **Issue #10**: Person-Liste Komponente - `high-priority`
5. **Issue #11**: Person-Formular Komponente - `high-priority`

Alle zu **Sprint 1** Milestone zuweisen.

## Tipps

- **Assignees**: Weise Issues Teammitgliedern zu
- **Due Dates**: Setze Fristen für wichtige Issues
- **Linked Issues**: Verlinke verwandte Issues mit `#issue-number`
- **Checklists**: Nutze die Akzeptanzkriterien als Checkliste im Issue

