import { test, expect, Page } from '@playwright/test';

test.describe('Sort Digits Game', () => {

    test('should be able to drag and drop in the game', async ({ page }) => {
        await page.goto('/');
        await page.locator('button[routerLink="/courses"]').click();
        await page.locator('button[data-test="memorizing"]').click();
        await page.locator('button[data-test="mnemonics"]').click();
        await expect(page.locator('div.mat-dialog-title')).toHaveText(' Cyferki ');
        await page.locator('button[data-test="start-test"]').click();
        await Promise.all([
            page.waitForResponse((res) => res.url().includes('/api/memorizing/guest/numbers/EASY') && res.status() === 200),
            page.locator('button.btn-chose-level').click()
        ]);
        await expect(page).toHaveURL(/\/courses\/memorizing\/level\/mnemonics;level=EASY/);
        const numberBox = page.locator('div[data-test="show-number"]');
        const initialText = await numberBox.textContent() ?? '';
        const initialNumber = parseInt(initialText ?? '', 10);
        expect(!isNaN(initialNumber)).toBe(true);
        await page.locator('button[data-test="show-next-number"]').click();
        await expect(numberBox).not.toHaveText(initialText);
        const updatedText = await numberBox.textContent();
        const updatedNumber = parseInt(updatedText ?? '', 10);
        expect(!isNaN(updatedNumber)).toBe(true);
        expect(updatedNumber).not.toEqual(initialNumber);
        for (let i = 0; i < 3; i++) {
            await page.locator('button[data-test="show-next-number"]').click();
        }
        await expect(numberBox).not.toBeVisible(); // Ensure number display disappears
        page.locator('div[data-test="drop-list-numbers"]');
        const initialOrder = await page.locator('[data-test="drop-list-numbers"] .example-box')
            .evaluateAll((items) => items.map(item => item.textContent?.trim()));
        const source = page.locator('[data-test="drop-list-numbers"] [cdkDrag]').first();
        const target = page.locator('[data-test="drop-list-numbers"] [cdkDrag]').nth(3); // Adjust index as needed
        const sourceBox = await source.boundingBox();
        const targetBox = await target.boundingBox();
        await page.mouse.move(sourceBox!.x + sourceBox!.width / 2, sourceBox!.y + sourceBox!.height / 2);
        await page.mouse.down();
        await page.mouse.move(targetBox!.x + targetBox!.width / 2, targetBox!.y + targetBox!.height / 2, { steps: 10 });
        await page.mouse.up(); // Drop the item
        await page.waitForSelector('[data-test="drop-list-numbers"] .cdk-drag-placeholder', { state: 'detached' });
        const newOrder = await page.locator('[data-test="drop-list-numbers"] .example-box')
            .evaluateAll(items => items.map(item => item.textContent?.trim()));
        expect(newOrder).not.toEqual(initialOrder);
    });
});