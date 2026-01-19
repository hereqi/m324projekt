// ***********************************************
// Custom Commands für E2E-Tests
// ***********************************************

/**
 * Custom Command: Person erstellen
 * Verwendet in Tests um schnell eine Test-Person anzulegen
 */
Cypress.Commands.add('createPerson', (name, vorname, thema, abgabedatum) => {
  cy.get('[data-testid="name-input"]').type(name)
  cy.get('[data-testid="vorname-input"]').type(vorname)
  cy.get('[data-testid="thema-input"]').type(thema)
  cy.get('[data-testid="abgabedatum-input"]').type(abgabedatum)
  cy.get('[data-testid="submit-button"]').click()
})

/**
 * Custom Command: API Mock für Tests ohne Backend
 */
Cypress.Commands.add('mockAPI', () => {
  cy.intercept('GET', '/api/personen', {
    statusCode: 200,
    body: []
  }).as('getPersonen')
  
  cy.intercept('GET', '/api/criteria', {
    statusCode: 200,
    body: [
      {
        id: 'C02',
        titel: 'Datenmodell entwickeln',
        leitfrage: 'Wurde ein professionelles Datenmodell entwickelt?',
        teil: 'Teil1',
        anforderungen: [
          'Anforderung 1',
          'Anforderung 2',
          'Anforderung 3',
          'Anforderung 4',
          'Anforderung 5',
          'Anforderung 6'
        ]
      }
    ]
  }).as('getCriteria')
})



