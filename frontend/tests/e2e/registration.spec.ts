import { test, expect, Page } from '@playwright/test';

async function checkValidationError(page: Page, inputSelector: string, invalidValue: string, expectedError: string) {
    const input = page.locator(inputSelector);
    await input.fill(invalidValue);
    await input.blur();
    await expect(input.locator('xpath=following-sibling::div[contains(@class, "invalid-data")]')).toHaveText(expectedError);
}

test('User registration form validation', async ({ page }) => {
    await page.goto('/');
    await page.locator('.nav-content [routerLink="/account"]').click();
    await page.locator('button[data-test="register-button"]').click();
    await checkValidationError(page, 'input#login', '123', ' Login musi mieć przynajmniej 5 znaków! ');
    await checkValidationError(page, 'input#email', 'bad_email', ' Adres e-mail jest niepoprawny! ');
    await checkValidationError(page, 'input#password', 'badPassword'
        , ' Hasło musi mieć przynajmniej 1 znak, 1 cyfrę, 1 małą i 1 dużą literę! ');
    await checkValidationError(page, 'input#confirmPassword', '', ' Potwierdzenie hasła jest wymagane! ');
    const submitButton = page.locator('button[type="submit"]');
    await expect(submitButton).toBeDisabled();
    await page.locator('button[type="reset"]').click();
    for (const selector of ['input#login', 'input#email', 'input#password', 'input#confirmPassword']) {
        await expect(page.locator(`${selector} + div.invalid-data`)).not.toBeVisible();
    }
    await page.fill('input#login', 'user_login');
    await page.fill('input#email', 'user@email.com');
    await page.fill('input#password', 'Password1#');
    await page.fill('input#confirmPassword', 'WrongPassword');
    const confirmPasswordInput = page.locator('input#confirmPassword');
    await expect(confirmPasswordInput).toHaveAttribute('type', 'password');
    await page.locator('button[data-test="confirmPasswordVisibility"]').click();
    await expect(confirmPasswordInput).toHaveAttribute('type', 'text');
    await confirmPasswordInput.fill('Password1#');
    await confirmPasswordInput.blur();
    await expect(submitButton).toBeEnabled();
});