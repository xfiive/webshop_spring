import Stripe from 'stripe';

export const useStripe = () => {
    const { $stripe } = useNuxtApp();

    const defaultParams: Stripe.Checkout.SessionCreateParams = {
        mode: 'payment',
        payment_method_types: ['card'],
    };

    const createCheckoutSession = async (params: Stripe.Checkout.SessionCreateParams) => {
        return await ($stripe as Stripe).checkout.sessions.create(params);
    };

    return {
        defaultParams,
        createCheckoutSession,
    };
};
