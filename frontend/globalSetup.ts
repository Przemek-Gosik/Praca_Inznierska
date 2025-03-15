import {FullConfig, request, expect, test as base} from '@playwright/test';
import config from 'playwright.config'
export default async function globalSetup(config: FullConfig) {
    const backendBaseUrl = config.metadata['backendURL'];
    const requestContext = await request.newContext();
    const response = await requestContext.post(`${backendBaseUrl}/reset-database`);
    expect(response.status()).toBe(200);
    console.log("Database reseted")
    await requestContext.dispose();

};