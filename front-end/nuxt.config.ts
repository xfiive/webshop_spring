export default defineNuxtConfig({
  modules: ['@nuxtjs/tailwindcss', '@pinia/nuxt'],
  devtools: { enabled: true },

  runtimeConfig: {
      stripeSecretKey: process.env.STRIPE_SECRET_KEY,
      public: {
          stripePublishableKey: process.env.STRIPE_PUBLISHABLE_KEY,
          apiUrl: process.env.API_URL
      }
  },

  imports: {
      dirs: ['stores', 'models']
  },

  routeRules: {
      '/admin/**': { appMiddleware: 'auth' }
  },

  compatibilityDate: '2024-09-16',
});