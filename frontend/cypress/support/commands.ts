Cypress.Commands.add('resetDB',()=>{
    const backendBaseUrl = Cypress.env('backendBaseUrl');
  cy.request('POST',`${backendBaseUrl}/reset-database`).then((res)=>{
      expect(res.status).to.eq(200);
  })
})

declare namespace Cypress {
        interface Chainable {
            resetDB(): Chainable<void>
        }
    }
