# Git Historie aufräumen - Merge Requests erstellen

## Übersicht

Die Git-Historie enthält einige Commits, die direkt auf `main` gemacht wurden. Um einen saubereren Arbeitsprozess zu zeigen, können diese in Feature Branches organisiert und als Pull Requests gemerged werden.

**⚠️ WICHTIG:** Da die Commits bereits gepusht wurden, sollten wir vorsichtig sein. Die beste Option ist, zukünftige Commits über Feature Branches zu machen.

---

## Aktuelle Situation

Die Historie zeigt bereits einige Merge Requests:
- ✅ `feature/docker-compose-db` → PR #10
- ✅ `feature/noten-berechnung` → PR #12
- ✅ `feature/kriterium-erfuellung-controller` → PR #13
- ✅ `feature/improvements` → gemerged
- ✅ `feature/test-documentation` → gemerged
- ✅ `feature/github-actions-ci` → gemerged

**Direkt auf main gemachte Commits:**
- CI/CD Fixes (Action-Version, YAML-Syntax, etc.)
- README Updates
- Dokumentation Updates

---

## Option 1: Zukünftige Commits über Feature Branches (Empfohlen)

Für alle zukünftigen Änderungen:

```bash
# 1. Feature Branch erstellen
git checkout -b feature/mein-feature

# 2. Änderungen machen und committen
git add .
git commit -m "feat: Feature implementieren"

# 3. Branch pushen
git push origin feature/mein-feature

# 4. Pull Request auf GitHub erstellen
# Gehe zu: https://github.com/hereqi/m324projekt/pulls
# Klicke auf "New Pull Request"
# Wähle: feature/mein-feature → main
# Erstelle PR

# 5. Nach Review: Merge auf GitHub
```

---

## Option 2: Bestehende Commits reorganisieren (Vorsichtig!)

**⚠️ NUR wenn du sicher bist, dass niemand anderes daran arbeitet!**

Falls du die bestehenden Commits reorganisieren möchtest:

### Schritt 1: Feature Branch für CI/CD Fixes erstellen

```bash
# Neuen Branch von einem früheren Commit erstellen
git checkout -b feature/ci-pipeline-fixes 20c694d

# Die CI-Fixes cherry-picken
git cherry-pick 0410dba
git cherry-pick a0bd650
git cherry-pick 20ffeb6

# Branch pushen
git push origin feature/ci-pipeline-fixes

# Pull Request erstellen auf GitHub
# Nach Merge: main aktualisieren
```

### Schritt 2: Dokumentation Updates

```bash
# Feature Branch für Dokumentation
git checkout -b feature/documentation-updates 9441665

# Dokumentation Commits cherry-picken
git cherry-pick f76fae4
git cherry-pick 20c694d
git cherry-pick 34cfcb7

# Branch pushen und PR erstellen
```

**⚠️ Problem:** Dies würde die Historie umschreiben und könnte Probleme verursachen, wenn andere daran arbeiten.

---

## Option 3: Nur zukünftige Arbeit organisieren (Sicherste Option)

Lass die bestehende Historie wie sie ist, aber organisiere alle zukünftigen Commits:

### Workflow für zukünftige Features:

```bash
# 1. Aktuellen Stand holen
git checkout main
git pull origin main

# 2. Feature Branch erstellen
git checkout -b feature/neues-feature

# 3. Arbeiten und committen
git add .
git commit -m "feat: Neues Feature"

# 4. Branch pushen
git push origin feature/neues-feature

# 5. Pull Request auf GitHub erstellen
# - Gehe zu: https://github.com/hereqi/m324projekt/pulls
# - Klicke auf "New Pull Request"
# - Wähle: feature/neues-feature → main
# - Beschreibe die Änderungen
# - Erstelle PR

# 6. Nach Review (oder automatisch wenn Tests grün sind): Merge
```

---

## Empfehlung

**Option 3 ist die sicherste:** Lass die bestehende Historie wie sie ist, aber organisiere alle zukünftigen Commits über Feature Branches und Pull Requests.

Die bestehende Historie zeigt bereits einen guten Arbeitsprozess mit mehreren gemergten Feature Branches. Die direkten Commits auf main sind hauptsächlich kleine Fixes (CI/CD, Dokumentation), was in vielen Projekten akzeptabel ist.

---

## Pull Request Template (Optional)

Erstelle `.github/pull_request_template.md`:

```markdown
## Beschreibung
[Was wurde geändert?]

## Änderungen
- [ ] Feature X implementiert
- [ ] Tests hinzugefügt
- [ ] Dokumentation aktualisiert

## Tests
- [ ] Unit-Tests bestanden
- [ ] Integration-Tests bestanden
- [ ] Manuelle Tests durchgeführt

## Checkliste
- [ ] Code folgt den Projekt-Standards
- [ ] Selbst-Review durchgeführt
- [ ] Kommentare hinzugefügt wo nötig
- [ ] Dokumentation aktualisiert
- [ ] Keine neuen Warnungen

## Related Issues
Closes #[Issue-Nummer]
```

