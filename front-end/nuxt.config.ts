// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    modules: ['@nuxtjs/tailwindcss', '@pinia/nuxt'],
    devtools: { enabled: true },
    runtimeConfig: {
        public: {
            apiUrl: process.env.API_URL
        }
    },
    imports: {
        dirs: ['stores', 'models']
    },
    routeRules: {
        '/admin/**': { appMiddleware: 'auth' }
    },
});
