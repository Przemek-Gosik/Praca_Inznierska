describe("Find numbers spec",()=>{
    const findNumber = () => {
        cy.get('span[data-test="find-number-text"]').as('findNumber').should('exist');
        cy.get('@findNumber').invoke('text').then((text)=>{
            let number = parseInt(text,10);
            cy.get(`button[data-test="number-${number}"]`).should('be.visible').click();
        });
    };
    const findWrongNumber = () => {
        cy.get('span[data-test="find-number-text"]').as('findNumber').should('exist');
        cy.get('@findNumber').invoke('text').then((text)=>{
            let number = parseInt(text,10);
            if(number == 16) {
                number = 15
            } else {
                number = number + 1;
            }
            cy.get(`button[data-test="number-${number}"]`).should('be.visible').click();
        });
    };

   it('should be able to start the game and pause at any time',()=>{
       cy.visit("/");
       cy.get('.nav-content').find('[routerLink="/courses/reading"]').should('be.visible').click();
       cy.get('button[data-test="find-numbers"]').should('exist').and('be.visible').click();
       cy.get('.mat-dialog-title').should('have.text'," Znajdowanie liczb ");
       cy.get('button[data-test="start-button"]').should('be.visible').click();
       cy.wait(1000);
       cy.get('input[ng-reflect-value="MEDIUM"]').should('exist').check({force:true});
       cy.intercept('GET','/api/fast_reading/text/guest/finding_numbers/MEDIUM').as('getNumbers');
       cy.get('button.btn-chose-level').should('be.visible').click();
       cy.wait('@getNumbers').its('response.statusCode').should('eq',200);
       cy.url().should('include','/courses/reading/level/finding_numbers;level=MEDIUM');
       cy.get('button[data-test="hidden-number').as('buttonsWithHiddenNumbers').should("exist").and('have.length',16);
       cy.get('.points').should('be.visible').and('contain.text','Zdobyte punkty: 0');
       cy.get('button[data-test="button-save-result"]').should('not.exist');
       cy.get('.timer').should('be.visible').and('contain.text','Czas: 00:00:00');
       cy.get('button[data-test="button-start-test"]').should('be.visible').and('be.enabled').click();
       cy.get('@buttonsWithHiddenNumbers').should('not.exist');
       findNumber();
       cy.get('span[data-test="points-text"').should('have.text','1');
       findWrongNumber();
       cy.get('span[data-test="points-text"').should('have.text','0');
       cy.get('button[data-test="button-start-test"]').should('have.text','Pauza').click();
       cy.get('span[data-test="time-text"]').invoke('text').then((value)=>{
           cy.wait(2000);
           cy.get('span[data-test="time-text"]').invoke('text').should('eq',value);
       });
   });
});