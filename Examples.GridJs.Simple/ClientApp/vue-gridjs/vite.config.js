import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

const backend = 'http://127.0.0.1:8080';

export default defineConfig({
  base: '/demos/vue/',
  plugins: [vue()],
  server: {
    proxy: {
      '/GridJs': { target: backend, changeOrigin: true },
    },
  },
  build: {
    outDir: '../../src/main/resources/static/demos/vue',
    emptyOutDir: true,
  },
});
