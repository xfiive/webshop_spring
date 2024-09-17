import Stripe from 'stripe';

export default defineNuxtPlugin(() => {
  const config = useRuntimeConfig();

  const stripe = new Stripe(config.stripeSecretKey as string, {
    apiVersion: '2024-06-20',
  });

  return {
    provide: {
      stripe,
    },
  };
});
