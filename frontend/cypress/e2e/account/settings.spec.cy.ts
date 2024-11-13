describe('User settings spec',()=>{
    it('should be able to change style in user settings',()=>{
       cy.visit('/');
        cy.get('.nav-content').find('[routerLink="/account"]').should('be.visible').click();
        cy.get('button[data-test="login-button"]')
    });
})