import { test, expect, Page } from '@playwright/test';
import { loginAsUser } from "../helper";

test.describe('Fast Typing Course', () => {

    const typeCharacter = async (page: Page, wordIndex: number, charIndex: number) => {
        const charElement = page.locator(`div[data-test="${wordIndex}char${charIndex}"]`);
        await expect(charElement).toBeVisible();
        const text = await charElement.innerText();
        await page.locator(`input[data-test="input${wordIndex}"]`).pressSequentially(text.trim(), { delay: 50 });
        await expect(charElement).toHaveCSS('color', 'rgb(0, 128, 0)');
    };

    test('should complete course and see completed percentage', async ({ page }) => {
        await loginAsUser(page);
        await page.locator('button[routerLink="/courses"]').click();
        await page.locator('button[data-test="writing"]').click();
        await page.locator('button[data-test="lessons"]').click();
        await expect(page.locator('.mat-dialog-title')).toBeVisible();
        await expect(page.locator('.mat-dialog-title')).toHaveText('Lekcje szybkiego pisania');
        await page.locator('button[data-test="start-task"]').click();
        await expect(page.locator('[data-test^="writing-module"]')).toHaveCount(5);
        await expect(page.locator('[data-test="lesson4"]')).not.toBeVisible();
        await page.locator('[data-test="writing-module0"]').click();
        await expect(page.locator('[data-test="lesson4"]')).toBeVisible();
        const [response] = await Promise.all([
            page.waitForResponse((res) => res.url().includes('/api/fast_writing/guest/lesson/4') && res.status() === 200),
            page.locator('[data-test="lesson4"]').click(),
        ]);
        expect(response.status()).toBe(200); // Ensure response was successful
        const inputField = page.locator('input[data-test="input0"]');
        await expect(inputField).toBeDisabled();
        await page.locator('[data-test="start-pause-button"]').click();
        await expect(inputField).toBeEnabled();
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 10; j++) {
                await typeCharacter(page, i, j);
            }
        }
        await page.locator('[data-test="start-pause-button"]').click();
        await page.locator('[data-test="calculate-button"]').click();
        await expect(page.locator('[data-test="points"]')).toBeVisible();
        await expect(page.locator('[data-test="points"]')).toHaveText(/Zdobyte punkty: \d+/);
        await expect(page.locator('[data-test="number-of-chars"]')).toHaveText(/Ilość wpisanych znaków: \d+/);
        await expect(page.locator('[data-test="precision"]')).toHaveText(/Precyzja: \d+\.\d+%/);
        await page.locator('[data-test="save-button"]').click();
        await page.locator('[data-test="close-button"]').click();
        await page.locator('[data-test="go-back-button"]').click();
        await expect(page).toHaveURL(/\/courses\/writing\/course/);
        await page.locator('[data-test="writing-module0"]').click();
        await expect(page.locator('[data-test="progress4"]')).toHaveAttribute('aria-valuenow', '80');
    });
});