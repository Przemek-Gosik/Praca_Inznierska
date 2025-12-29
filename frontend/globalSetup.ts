import {FullConfig, request, expect} from '@playwright/test';
export default async function globalSetup(config: FullConfig) {
    const backendBaseUrl = config.metadata['backendURL'];
    const requestContext = await request.newContext();
    const response = await requestContext.post(`${backendBaseUrl}/reset-database`);
    expect(response.status()).toBe(200);
    await requestContext.dispose();
};