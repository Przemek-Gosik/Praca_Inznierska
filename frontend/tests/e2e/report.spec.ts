import { test, expect } from '@playwright/test';
import {loginAsAdmin, loginAsUser} from "../helper";

test.describe('Report Management', () => {
    test('should be able to report a problem as a user and see it as an admin', async ({ browser }) => {
        const userContext = await browser.newContext();
        const adminContext = await browser.newContext();
        const userPage = await userContext.newPage();
        const adminPage = await adminContext.newPage();

        try {
            await loginAsUser(userPage);
            await userPage.goto('/contact');
            await userPage.fill('input#email', "user@email.com");
            await userPage.fill('input#title', "This is user report");
            await userPage.fill('textarea#message', "Hello writing because my account has been blocked, please fix this");
            const [response] = await Promise.all([
                userPage.waitForResponse(res => res.url().includes('/api/report') && res.status() === 201, { timeout: 20000 }),
                userPage.click('button[type="submit"]')
            ]);
            expect(response.ok()).toBeTruthy();
            await loginAsAdmin(adminPage);
            await adminPage.goto('/account');
            await adminPage.click('button[data-test="reports-button"]');
            const reportRow = adminPage.locator('table[mat-table] tbody tr', {
                has: adminPage.locator('td', { hasText: 'This is user report' })
            });
            await reportRow.waitFor({ timeout: 15000 });
            await reportRow.locator('button').click();
            await expect(adminPage.locator('app-report-details-dialog')).toBeVisible();
            await expect(adminPage.locator('div[mat-dialog-title]')).toHaveText('This is user report');
            const dialog = adminPage.locator('div[mat-dialog-content]');
            await expect(dialog.locator('strong', { hasText: 'Date:' }).locator('..')).not.toBeEmpty();
            await expect(dialog.locator('strong', { hasText: 'Email:' }).locator('..')).toContainText('user@email.com');
            await expect(dialog).toContainText("Hello writing because my account has been blocked, please fix this");
            await adminPage.locator('button[mat-dialog-close]').click();
            await expect(adminPage.locator('div[mat-dialog-title]')).not.toBeVisible();
        } finally {
            await Promise.all([
                userContext.close(),
                adminContext.close()
            ]);
        }
    });
});
