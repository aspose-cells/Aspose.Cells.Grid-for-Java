import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

const backend = 'http://127.0.0.1:8080';

export default defineConfig({
  base: '/demos/react/',
  plugins: [react()],
  server: {
    proxy: {
      '/GridJs': { target: backend, changeOrigin: true },
    },
  },
  build: {
    outDir: '../../src/main/resources/static/demos/react',
    emptyOutDir: true,
  },
});
