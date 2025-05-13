import { test, expect, Page } from '@playwright/test';

test.describe('Find Numbers Game', () => {
    const findNumber = async (page: Page) => {
        const findNumberText = page.locator('span[data-test="find-number-text"]');
        const numberText = await findNumberText.innerText();
        const number = parseInt(numberText, 10);
        const numberButton = page.locator(`button[data-test="number-${number}"]`);
        await numberButton.click();
    };

    const findWrongNumber = async (page: Page) => {
        const findNumberText = page.locator('span[data-test="find-number-text"]');
        let number = parseInt(await findNumberText.innerText(), 10);
        number = (number === 16) ? 15 : number + 1;
        const wrongButton = page.locator(`button[data-test="number-${number}"]`);
        await wrongButton.click();
    };

    test('should start the game and allow pausing', async ({ page }) => {
        await page.goto('/');
        await page.locator('.nav-content [routerLink="/courses/reading"]').click();
        await page.locator('button[data-test="find-numbers"]').click();
        await expect(page.locator('.mat-dialog-title')).toHaveText(' Znajdowanie liczb ');
        await page.locator('button[data-test="start-button"]').click();
        await page.locator('label:has-text("Średni")').click();
        const [response] = await Promise.all([
            page.waitForResponse((res) => res.url().includes('/api/fast_reading/text/guest/finding_numbers/MEDIUM') && res.status() === 200),
            page.locator('button.btn-chose-level').click()
        ]);
        await expect(page).toHaveURL(/\/courses\/reading\/level\/finding_numbers;level=MEDIUM/);
        const buttonsWithHiddenNumbers = page.locator('button[data-test="hidden-number"]');
        await expect(buttonsWithHiddenNumbers).toHaveCount(16);
        await expect(page.getByText('Zdobyte punkty: 0', { exact: false })).toBeVisible();
        await expect(page.locator('button[data-test="button-save-result"]')).not.toBeVisible();
        await expect(page.locator('.timer')).toContainText('Czas: 00:00:00');
        const startTestButton = page.locator('button[data-test="button-start-test"]');
        await expect(startTestButton).toBeEnabled();
        await startTestButton.click();
        await expect(buttonsWithHiddenNumbers).toHaveCount(0); // Numbers should disappear
        await findNumber(page);
        await expect(page.locator('span[data-test="points-text"]')).toHaveText('1');
        await findWrongNumber(page);
        await expect(page.locator('span[data-test="points-text"]')).toHaveText('0');
        await expect(startTestButton).toHaveText('Pauza');
        await startTestButton.click();
        const timeText = page.locator('span[data-test="time-text"]');
        const initialTime = (await timeText.textContent()) ?? '';
        await expect(timeText).toHaveText(initialTime);
    });
});