import {Page, expect} from "@playwright/test";

export async function loginAsUser(page: Page) {
    await page.goto('/');

    // Click on account link
    await page.locator('.nav-content a[routerLink="/account"]').click();

    // Click on login button
    await page.locator('button[data-test="login-button"]').click();

    // Fill login form
    await page.locator('input#login').fill('user');
    await page.locator('input#password').fill('Password1!');

    // Intercept the login request
    const [response] = await Promise.all([
        page.waitForResponse((res) => res.url().includes('/api/auth/login') && res.status() === 200),
        page.locator('button[type="submit"]').click(), // Submit the login form
    ]);

    // Ensure login was successful
    expect(response.status()).toBe(200);
    await expect(page).toHaveURL(/\/home/);
}

export async function loginAsAdmin(page: Page) {
    await page.goto('/');

    // Click on account link
    await page.locator('.nav-content a[routerLink="/account"]').click();

    // Click on login button
    await page.locator('button[data-test="login-button"]').click();

    // Fill login form
    await page.locator('input#login').fill('admin');
    await page.locator('input#password').fill('Password1!');

    // Intercept the login request
    const [response] = await Promise.all([
        page.waitForResponse((res) => res.url().includes('/api/auth/login') && res.status() === 200),
        page.locator('button[type="submit"]').click(), // Submit the login form
    ]);

    // Ensure login was successful
    expect(response.status()).toBe(200);
    await expect(page).toHaveURL(/\/home/);
}