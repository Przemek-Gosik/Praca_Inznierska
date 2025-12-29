describe('User settings spec', () => {
    it('should be able to change style in user settings', () => {
        cy.visit('/');
        cy.get('.nav-content').find('[routerLink="/account"]').should('be.visible').click();
        cy.get('button[data-test="login-button"]').click();
        cy.get('button[type="submit"]').as('loginButton').should('exist').and('be.disabled');
        cy.get('input#login').focus().type("user");
        cy.get('input#password').focus().type("Password1!");
        cy.intercept('POST', '/api/auth/login').as('login');
        cy.get('@loginButton').should('be.enabled').click();
        cy.wait('@login').its('response.statusCode').should('eq', 200);
        cy.url().should('include', '/home');
        cy.get('.nav-content').find('[routerLink="/settings"]').should('be.visible').click();
        cy.get('body').should('have.class', 'DAY').and('have.css', 'background-color', 'rgb(255, 255, 255)');
        cy.get('.context-box').should('have.css', 'background-color', 'rgba(92, 170, 215, 0.518)');
        cy.intercept('PATCH', '/api/auth/changeSetting').as('changeSetting');
        cy.get('button[data-test="button-NIGHT"]').should('exist').and('have.css', 'background-color', 'rgba(3, 86, 137, 0.624)')
            .and('have.css', 'color', 'rgb(0, 0, 0)').click();
        cy.wait(500);
        cy.wait('@changeSetting').its('response.statusCode').should('eq', 200);
        cy.get('body').should('have.class', 'NIGHT');
        cy.get('.context-box').should('have.css', 'background-color', 'rgba(19, 78, 112, 0.32)');
        cy.get('button[data-test="button-CONTRAST"]').should('exist').and('have.css', 'background-color', 'rgba(116, 133, 143, 0.624)')
            .and('have.css', 'color', 'rgba(255, 255, 255, 0.706)').click();
        cy.wait(500);
        cy.wait('@changeSetting').its('response.statusCode').should('eq', 200);
        cy.get('body').should('have.class', 'CONTRAST');
        cy.get('.context-box').should('have.css', 'background-color', 'rgb(180, 180, 180)');
        cy.get('button[data-test="button-CONTRAST"]').should('exist').and('have.css', 'background-color', 'rgba(229, 233, 235, 0.87)')
            .and('have.css', 'color', 'rgb(0, 0, 0)');
    });
})