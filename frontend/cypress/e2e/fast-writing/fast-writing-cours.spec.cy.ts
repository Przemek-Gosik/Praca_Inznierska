describe("Fast typing course spec", () => {
    const typeCharacter = (wordIndex: number, charIndex: number) => {
        cy.get(`div[data-test="${wordIndex}char${charIndex}"]`).should('have.css', 'color', 'rgb(0, 0, 0)')
            .invoke('text').then((text) => {
            cy.get(`input[data-test="input${wordIndex}"]`).focus().should('be.enabled').type(text.trim());
            cy.get(`div[data-test="${wordIndex}char${charIndex}"]`).should('have.css', 'color', 'rgb(0, 128, 0)');
        });
    };

    it('should be able to complete course and see completed percentage', () => {
        cy.visit("/");
        cy.loginAsUser();
        cy.get('button[routerLink="/courses"]').should('be.visible').click();
        cy.get('button[data-test="writing"]').should('exist').click();
        cy.get('button[data-test="lessons"]').should('exist').click();
        cy.get('.mat-dialog-title').should('have.text', 'Lekcje szybkiego pisania');
        cy.intercept('GET', '/api/fast_writing').as("getLessons");
        cy.get('button[data-test="start-task"]').click();
        cy.wait('@getLessons').its('response.statusCode').should('eq', 200);
        cy.get('[data-test^="writing-module"]').then((elements) => {
            const count = elements.length;
            expect(count).to.be.equal(5);
        });
        cy.get('[data-test="lesson4"]').should('not.be.visible');
        cy.get('[data-test="writing-module0"]').click();
        cy.intercept('GET', 'api/fast_writing/guest/lesson/4').as('getSpecialLesson');
        cy.get('[data-test="progress4"]').should('exist').invoke('attr','aria-valuenow').then((value)=>{
            expect(value).to.be.eq('0');
        });
        cy.get('[data-test="lesson4"]').should('be.visible').click();
        cy.wait('@getSpecialLesson').its('response.statusCode').should('eq', 200);
        cy.get('input[data-test="input0"]').should('be.disabled');
        cy.get('[data-test="start-pause-button"]').click();
        cy.wait(100);
        cy.get('input[data-test="input0"]').should('be.enabled');
        cy.get('[data-test="calculate-button"]').should('be.disabled');
        for (let i = 0; i < 4; i++){
            for(let j=0; j < 10; j++){
                typeCharacter(i,j);
            }
        }
        cy.get('[data-test="start-pause-button"]').click();
        cy.get('[data-test="calculate-button"]').should('be.enabled').click();
        cy.wait(100);
        cy.get('[data-test="points"]').should('have.text','Zdobyte punkty: 40');
        cy.get('[data-test="number-of-chars"]').should('have.text','Ilość wpisanych znaków: 40');
        cy.get('[data-test="precision"]').should('have.text','Precyzja: 100.00%');
        cy.intercept('POST','api/fast_writing/lesson/result').as("saveResult");
        cy.get('[data-test="save-button"]').should('be.enabled').click();
        cy.wait('@saveResult').its('response.statusCode').should('eq',201);
        cy.get('[data-test="close-button"]').click();
        cy.get('[data-test="go-back-button"]').should('be.visible').click();
        cy.url().should('include','/courses/writing/course');
        cy.get('[data-test="writing-module0"]').click();
        cy.wait(500);
        cy.get('[data-test="progress4"]').should('exist').invoke('attr','aria-valuenow').then((value)=>{
            expect(value).to.be.eq('80');
        });
    });
});
