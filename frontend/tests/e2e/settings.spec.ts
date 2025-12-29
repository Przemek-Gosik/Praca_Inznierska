 import { test, expect } from '@playwright/test';
 import {loginAsUser} from "../helper";

test.describe('User Settings', () => {
    test('should be able to change style in user settings', async ({ page }) => {
        await page.goto('/');
        await loginAsUser(page); // ✅ Reusable login function
        await page.goto('/settings'); // ✅ Directly go to settings instead of navigating

        // ✅ Check initial "DAY" mode
        await expect(page.locator('.context-box')).toHaveCSS('background-color', 'rgba(92, 170, 215, 0.518)');
        const initialBodyClass = await page.evaluate(() => document.body.className);
        expect(initialBodyClass).toContain('DAY');

        // ✅ Change to NIGHT mode
        await page.locator('button[data-test="button-NIGHT"]').click();
        await page.waitForResponse(res => res.url().includes('/api/auth/changeSetting') && res.status() === 200);
        const nightBodyClass = await page.evaluate(() => document.body.className);
        expect(nightBodyClass).toContain('NIGHT');
        await expect(page.locator('.context-box')).toHaveCSS('background-color', 'rgba(19, 78, 112, 0.32)');

        // ✅ Change to CONTRAST mode
        await page.locator('button[data-test="button-CONTRAST"]').click();
        await page.waitForResponse(res => res.url().includes('/api/auth/changeSetting') && res.status() === 200);
        const contrastBodyClass = await page.evaluate(() => document.body.className);
        expect(contrastBodyClass).toContain('CONTRAST');
        await expect(page.locator('.context-box')).toHaveCSS('background-color', 'rgb(180, 180, 180)');

        // ✅ Verify final button styles
        await expect(page.locator('button[data-test="button-CONTRAST"]'))
            .toHaveCSS('background-color', 'rgba(229, 233, 235, 0.87)');
        await expect(page.locator('button[data-test="button-CONTRAST"]'))
            .toHaveCSS('color', 'rgb(0, 0, 0)');
    });
});