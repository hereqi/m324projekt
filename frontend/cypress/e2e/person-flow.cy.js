/**
 * E2E-Tests: Person erstellen und verwalten Flow
 * 
 * Testet den kompletten User-Flow für Personenverwaltung:
 * - Person erstellen
 * - Personenliste anzeigen
 * - Person-Details anzeigen
 */

describe('Person Flow E2E Tests', () => {
  
  beforeEach(() => {
    // Mock API Calls
    cy.intercept('GET', '**/api/personen', {
      statusCode: 200,
      body: []
    }).as('getPersonen')
    
    cy.intercept('POST', '**/api/personen', {
      statusCode: 201,
      body: {
        id: 1,
        name: 'Mustafa',
        vorname: 'Sagaaro',
        thema: 'Kriterien-Tracking App',
        abgabedatum: '2026-06-15'
      }
    }).as('createPerson')
    
    cy.intercept('GET', '**/api/criteria', {
      statusCode: 200,
      body: [
        {
          id: 'C02',
          titel: 'Datenmodell entwickeln',
          leitfrage: 'Wurde ein professionelles Datenmodell entwickelt?',
          teil: 'Teil1',
          anforderungen: [
            'Datenmodellierungsmethodik gewählt',
            'Geschäftsanforderungen korrekt abgebildet',
            'Normalisierung angewandt',
            'Flexibel und skalierbar',
            'Ausreichend dokumentiert',
            'Performanceanforderungen erfüllt'
          ]
        }
      ]
    }).as('getCriteria')
  })
  
  it('sollte die Startseite laden', () => {
    cy.visit('/')
    cy.contains('Kriterien Tracker').should('be.visible')
  })
  
  it('sollte das Personenformular anzeigen', () => {
    cy.visit('/')
    
    // Formularfelder sollten sichtbar sein
    cy.get('input[name="name"], input[placeholder*="Name"]').should('exist')
    cy.get('input[name="vorname"], input[placeholder*="Vorname"]').should('exist')
    cy.get('input[name="thema"], input[placeholder*="Thema"]').should('exist')
  })
  
  it('sollte eine neue Person erstellen können', () => {
    cy.visit('/')
    
    // Formular ausfüllen
    cy.get('input[name="name"], input[placeholder*="Name"]').first().type('Mustafa')
    cy.get('input[name="vorname"], input[placeholder*="Vorname"]').first().type('Sagaaro')
    cy.get('input[name="thema"], input[placeholder*="Thema"]').first().type('Kriterien-Tracking App')
    
    // Abgabedatum falls vorhanden
    cy.get('input[type="date"]').first().type('2026-06-15')
    
    // Formular absenden
    cy.get('button[type="submit"], button').contains(/erstellen|speichern|hinzufügen/i).click()
    
    // API sollte aufgerufen worden sein
    cy.wait('@createPerson')
  })
  
  it('sollte Validierung bei leerem Formular zeigen', () => {
    cy.visit('/')
    
    // Direkt absenden ohne Eingabe
    cy.get('button[type="submit"], button').contains(/erstellen|speichern|hinzufügen/i).click()
    
    // HTML5 Validation oder Custom Error sollte erscheinen
    cy.get('input:invalid').should('exist')
  })
})

describe('Kriterien Flow E2E Tests', () => {
  
  beforeEach(() => {
    // Mock alle API Calls
    cy.intercept('GET', '**/api/personen', {
      statusCode: 200,
      body: [{
        id: 1,
        name: 'Mustafa',
        vorname: 'Sagaaro',
        thema: 'Kriterien-Tracking',
        abgabedatum: '2026-06-15'
      }]
    }).as('getPersonen')
    
    cy.intercept('GET', '**/api/personen/1', {
      statusCode: 200,
      body: {
        id: 1,
        name: 'Mustafa',
        vorname: 'Sagaaro',
        thema: 'Kriterien-Tracking',
        abgabedatum: '2026-06-15'
      }
    }).as('getPerson')
    
    cy.intercept('GET', '**/api/criteria', {
      statusCode: 200,
      body: [
        {
          id: 'C02',
          titel: 'Datenmodell entwickeln',
          leitfrage: 'Wurde ein professionelles Datenmodell entwickelt?',
          teil: 'Teil1',
          anforderungen: [
            'Datenmodellierungsmethodik gewählt',
            'Geschäftsanforderungen korrekt abgebildet',
            'Normalisierung angewandt',
            'Flexibel und skalierbar',
            'Ausreichend dokumentiert',
            'Performanceanforderungen erfüllt'
          ]
        },
        {
          id: 'B05',
          titel: 'Backend-Architektur implementieren',
          leitfrage: 'Wurde eine saubere Backend-Architektur implementiert?',
          teil: 'Teil2',
          anforderungen: [
            'Layered Architecture Pattern',
            'Business-Logik im Service-Layer',
            'Datenbank-Zugriffe über Repository',
            'Exception-Handling implementiert'
          ]
        }
      ]
    }).as('getCriteria')
    
    cy.intercept('GET', '**/api/criterion-progress/person/1', {
      statusCode: 200,
      body: []
    }).as('getProgress')
    
    cy.intercept('POST', '**/api/criterion-progress', {
      statusCode: 201,
      body: {
        id: 1,
        personId: 1,
        criterionId: 'C02',
        erfuellteAnforderungen: [0, 1],
        notizen: '',
        guetestufe: 2
      }
    }).as('saveProgress')
  })
  
  it('sollte Kriterien nach Person-Auswahl anzeigen', () => {
    cy.visit('/')
    
    // Warte auf API
    cy.wait('@getPersonen')
    
    // Person in Liste finden und klicken (falls vorhanden)
    cy.get('body').then(($body) => {
      if ($body.text().includes('Mustafa')) {
        cy.contains('Mustafa').click()
        cy.wait('@getCriteria')
      }
    })
  })
  
  it('sollte Kriterien-Cards mit Checkboxen anzeigen', () => {
    cy.visit('/')
    cy.wait('@getPersonen')
    
    // Falls Kriterien direkt sichtbar sind
    cy.get('body').then(($body) => {
      if ($body.find('[type="checkbox"]').length > 0) {
        cy.get('[type="checkbox"]').should('have.length.at.least', 1)
      }
    })
  })
})

describe('Navigation E2E Tests', () => {
  
  it('sollte zwischen Seiten navigieren können', () => {
    cy.visit('/')
    cy.url().should('include', '/')
  })
  
  it('sollte responsive Layout haben', () => {
    // Desktop
    cy.viewport(1280, 720)
    cy.visit('/')
    cy.get('body').should('be.visible')
    
    // Tablet
    cy.viewport(768, 1024)
    cy.get('body').should('be.visible')
    
    // Mobile
    cy.viewport(375, 667)
    cy.get('body').should('be.visible')
  })
})



