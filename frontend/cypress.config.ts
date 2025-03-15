import { defineConfig } from "cypress";

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:4200',
    env: {
      backendBaseUrl: 'http://127.0.0.1:8080'
    },
  },
});
