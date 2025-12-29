import {Page, expect} from "@playwright/test";

export async function loginAsUser(page: Page) {
    await login(page, 'login', 'Password1!');
}

export async function loginAsAdmin(page: Page) {
    await login(page, 'admin', 'Password1!');
}

async function login(page: Page,login: string, password: string) {
    await page.goto('/');
    await page.locator('.nav-content a[routerLink="/account"]').click();
    await page.locator('button[data-test="login-button"]').click();
    await page.locator('input#login').fill(login);
    await page.locator('input#password').fill(password);

    const [response] = await Promise.all([
        page.waitForResponse((res) => res.url().includes('/api/auth/login') && res.status() === 200),
        page.locator('button[type="submit"]').click(), // Submit the login form
    ]);
    expect(response.status()).toBe(200);
    await expect(page).toHaveURL(/\/home/);
}