import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'
import sveltePreprocess from 'svelte-preprocess'

export default defineConfig({
    define: {
        "import.meta.env.API_URL": JSON.stringify("http://localhost:8080")
    },
    plugins: [svelte({
        preprocess: sveltePreprocess()
    })],
    server: {
        port: 8000,
        host: true,
        watch: {
            usePolling: true
        }
    }
})
