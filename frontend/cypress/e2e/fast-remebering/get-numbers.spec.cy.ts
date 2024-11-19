describe("Sort digits spec", () => {

    it('should be able to drag and drop in the game', () => {
        cy.visit("/");
        cy.get('button[routerLink="/courses"]').should('be.visible').click();
        cy.get('button[data-test="memorizing"]').should('exist').click();
        cy.get('button[data-test="mnemonics"]').should('exist').click({waitForAnimations: true});
        cy.get('div.mat-dialog-title').should('be.visible').and('have.text', ' Cyferki ');
        cy.get('button[data-test="start-test"]').should('exist').click({waitForAnimations: true});
        cy.intercept('GET', '/api/memorizing/guest/numbers/EASY').as('getMnemonics');
        cy.get('button.btn-chose-level').should('be.visible').click();
        cy.wait('@getMnemonics').its('response.statusCode').should('eq', 200);
        cy.url().should('include', '/courses/memorizing/level/mnemonics;level=EASY');
        cy.wait(500);
        cy.get('div[data-test="show-number"]')
            .should('exist')
            .invoke('text')
            .then((text) => {
                const number = parseInt(text);
                expect(isNaN(number)).to.be.false;

                cy.get('button[data-test="show-next-number"]').click({waitForAnimations: true});
                cy.wait(500);
                cy.get('div[data-test="show-number"]').invoke('text').then((updatedText) => {
                    const updatedNumber = parseInt(updatedText);
                    expect(updatedNumber).to.not.equal(number);
                });
            });
        cy.repeat(3, () => {
            cy.get('button[data-test="show-next-number"]').click({waitForAnimations: true});
        });
        cy.get('div[data-test="show-number"]').should('not.exist');
        cy.get('div[data-test="drop-list-numbers"]').should('exist');

        cy.get('[data-test="drop-list-numbers"] .example-box').then((items) => {
            // @ts-ignore
            const initialOrder = [...items].map((item) => item.textContent.trim());
            cy.log('Initial Order:', initialOrder[0]);
            const sourceSelector = `[data-test="drag-number-${initialOrder[3]}"]`;
            const targetSelector = '[data-test="drop-list-numbers"]';
            cy.get(targetSelector).then(($drop) => {
                const dropRect = $drop[0].getBoundingClientRect();
                cy.get(sourceSelector)
                    .trigger('mousedown', {
                        button: 0,
                        bubbles: true
                    })
                    .trigger('mousemove', {
                        pageX: dropRect.x,
                        pageY: dropRect.y,
                    }).wait(500);
                cy.get(targetSelector)
                    .trigger('mousemove', {
                        clientX: dropRect.x,
                        clientY: dropRect.y
                    })
                    .trigger('mouseup', {button: 0});
            });
            cy.wait(1000);
            // Capture the new order of elements
            cy.get('[data-test="drop-list-numbers"] .example-box').then((updatedItems) => {
                // @ts-ignore
                const newOrder = [...updatedItems].map((item) => item.textContent.trim());
                cy.log('New Order:', newOrder);
                expect(newOrder).to.not.deep.equal(initialOrder);
            });
        });
    });
});