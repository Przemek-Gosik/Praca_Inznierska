Cypress.Commands.add('resetDB', () => {
    const backendBaseUrl = Cypress.env('backendBaseUrl');
    cy.request('POST', `${backendBaseUrl}/reset-database`).then((res) => {
        expect(res.status).to.eq(200);
    })
});

Cypress.Commands.add('loginAsUser', () => {
    cy.get('.nav-content').find('a[routerLink="/account"]').should('be.visible').click();
    cy.get('button[data-test="login-button"]').should('exist').click();
    cy.get('input#login').focus().type("user");
    cy.get('input#password').focus().type("Password1!");
    cy.intercept('POST', '/api/auth/login').as('login');
    cy.get('button[type="submit"]').should('exist').and('be.enabled').click();
    cy.wait('@login').its('response.statusCode').should('eq', 200);
    cy.url().should('include', '/home');
});

Cypress.Commands.add('repeat', (times: number, action: () => void) => {
    for (let i = 0; i < times; i++) {
        action();
    }
});

declare namespace Cypress {
    interface Chainable {
        resetDB(): Chainable<void>,

        loginAsUser(): Chainable<void>

        repeat(times: number, action: () => void): Chainable<void>
    }
}
