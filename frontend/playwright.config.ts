import { defineConfig, devices } from '@playwright/test';
import * as dotenv from 'dotenv';

dotenv.config();

export default defineConfig({
  testDir: './tests/e2e',
  fullyParallel: false,
  retries: 0,
  use: {
    baseURL: process.env['FRONTEND_URL'] || 'http://localhost:4200', // Frontend URL
    trace: 'on-first-retry',
    video: 'retain-on-failure',
    screenshot: 'only-on-failure',
    ignoreHTTPSErrors: true,
  },
  webServer: {
    command: 'npm start',
    port: 4200,
    timeout: 60 * 1000,
    reuseExistingServer: true
  },
  metadata: {
    backendURL: process.env['BACKEND_URL'] || 'http://localhost:8080', // Backend URL
  },
  globalSetup: require.resolve('./globalSetup'),
});
