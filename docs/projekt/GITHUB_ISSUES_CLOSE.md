# GitHub Issues schließen - Anleitung

## Übersicht

Alle 26 Issues aus `GITHUB_ISSUES.md` sind implementiert. Diese Anleitung zeigt, wie man die Issues auf GitHub schließt.

---

## Schritt 1: Issues auf GitHub schließen

### Option A: Manuell schließen

1. Gehe zu: https://github.com/hereqi/m324projekt/issues
2. Für jedes Issue:
   - Öffne das Issue
   - Klicke auf "Close issue" (rechts oben)
   - Optional: Füge einen Kommentar hinzu:
     ```
     ✅ Implementiert
     
     Siehe: docs/projekt/ISSUE_STATUS.md
     ```

### Option B: Mit Commit-Message schließen

Beim nächsten Commit kannst du Issues automatisch schließen:
```bash
git commit -m "docs: Alle Issues implementiert

Closes #1
Closes #2
Closes #3
# ... usw.
```

### Option C: Mit GitHub CLI (falls installiert)

```bash
# Alle offenen Issues auflisten
gh issue list --state open

# Alle Issues schließen
gh issue list --state open | awk '{print $1}' | xargs -I {} gh issue close {} --comment "✅ Implementiert - siehe docs/projekt/ISSUE_STATUS.md"
```

---

## Liste der zu schließenden Issues

Alle 26 Issues können geschlossen werden:
- Epic 1: Issues #1-3
- Epic 2: Issues #4-8
- Epic 3: Issues #9-14
- Epic 4: Issues #15-19
- Epic 5: Issues #20-24
- Epic 6: Issues #25-26

Siehe `ISSUE_STATUS.md` für Details.
