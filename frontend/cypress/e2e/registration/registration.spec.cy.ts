describe('template spec', () => {
  it('passes', () => {
    cy.visit('/');
    cy.get('.nav-content').find('[routerLink="/account"]').should('be.visible').click();
    cy.get('button[data-test="register-button"]').should('exist')
        .and('be.visible').click();
    cy.get("input#login").as('loginInput').should('exist')
        .and('be.enabled')
        .focus().type('123');
    cy.get('@loginInput').blur();
    cy.get('@loginInput').nextAll('div.invalid-data').should('be.visible')
        .and('have.text',' Login musi mieć przynajmniej 5 znaków! ');
    cy.get('input#email').as('emailInput').should('exist')
        .and('be.enabled')
        .focus().type('bad_email');
    cy.get('@emailInput').blur();
    cy.get('@emailInput').nextAll('div.invalid-data').should('be.visible')
        .and('have.text', ' Adres e-mail jest niepoprawny! ');
    cy.get('input#password').as('passwordInput').should('exist').and('be.enabled')
        .focus().type('badPassword');
    cy.get('@passwordInput').blur();
    cy.get('@passwordInput').nextAll('div.invalid-data').should('be.visible')
        .and('have.text', ' Hasło musi mieć przynajmniej 1 znak, 1 cyfrę, 1 małą i 1 dużą literę! ');
    cy.get('input#confirmPassword').as('confirmPasswordInput').should('exist')
        .and('be.visible').focus().blur();
    cy.get('@confirmPasswordInput').nextAll('div.invalid-data').should('be.visible')
        .and('have.text', ' Potwierdzenie hasła jest wymagane! ')
    cy.get('button[type="submit"]').should('exist').and('not.be.enabled');
    cy.get('button[type="reset"').should('exist').and('be.visible').click();
    cy.get('@loginInput').nextAll('div.invalid-data').should('not.exist');
    cy.get('@emailInput').nextAll('div.invalid-data').should('not.exist');
    cy.get('@passwordInput').nextAll('div.invalid-data').should('not.exist');
    cy.get('@confirmPasswordInput').nextAll('div.invalid-data').should('not.exist');
    cy.get('@loginInput').focus().type('user_login');
    cy.get('@emailInput').focus().type('user@email.com');
    cy.get('@passwordInput').focus().type('Password1#');
    cy.get('@confirmPasswordInput').focus().type('WrongPassword');
    cy.get('@confirmPasswordInput').should('have.attr','type','password');
    cy.get('button[data-test="confirmPasswordVisibility"]').should('be.visible').click();
    cy.get('@confirmPasswordInput').should('have.attr','type','text');
    cy.get('@confirmPasswordInput').clear().type('Password1#').blur();
    cy.get('button[type="submit"]').should('be.enabled');
  })
})