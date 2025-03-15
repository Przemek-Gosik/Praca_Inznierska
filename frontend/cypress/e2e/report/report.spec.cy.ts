describe("Report managment spec",()=>{
   it('should be able to report problem as user and see that as admin',()=>{
       cy.visit("/");
       cy.loginAsUser();
       cy.get('.nav-content').find('[routerLink="/contact"]').should('be.visible').click();
       cy.get('input#email').focus().type("user@email.com");
       cy.get('input#title').focus().type("This is user report");
       cy.get('textarea#message').focus().type('Hello writing because my account has been blocked, please fix this');
       cy.intercept('POST','/api/report').as('sendReport');
       cy.get('button[type="submit"]').should('be.enabled').click();
       cy.wait('@sendReport').its('response.statusCode').should('eq',201);
       cy.logout();
       cy.loginAsAdmin();
       cy.get('.nav-content').find('a[routerLink="/account"]').should('be.visible').click();
       cy.get('button[data-test="reports-button"]').should('exist').click();
       cy.get('table[mat-table] tbody')
           .contains('td', 'This is user report')
           .parent()
           .find('button')
           .click();
       cy.wait(1000);
       cy.get('app-report-details-dialog').should("be.visible");
       cy.get('div[mat-dialog-title]').should('have.text', 'This is user report');
       cy.get('div[mat-dialog-content]').within(() => {
           cy.contains('strong', 'Date:')
               .next().should('not.be.empty'); // Check Date is present
           cy.contains('strong', 'Email:')
               .parent().should('contain', 'user@email.com');
           cy.contains('strong', 'Text:'); // Ensure "Text:" label exists
           cy.contains('Hello writing because my account has been blocked, please fix this'); // Check the actual text content
       });
       cy.get('button[mat-dialog-close]').click();
       cy.get('div[mat-dialog-title]').should('not.exist');
   })
});