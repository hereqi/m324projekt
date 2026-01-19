// ***********************************************************
// This file is processed and loaded automatically before test files.
// ***********************************************************

// Import commands
import './commands'

// Prevent Cypress from failing tests on uncaught exceptions
Cypress.on('uncaught:exception', (err, runnable) => {
  // returning false here prevents Cypress from failing the test
  return false
})



