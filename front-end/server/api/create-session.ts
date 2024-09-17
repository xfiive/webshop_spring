export default defineEventHandler(async (event) => {
    const { createCheckoutSession, defaultParams } = useStripe();

    const body = await readBody(event);

    try {
        const session = await createCheckoutSession({
            ...defaultParams,

        });

        return { sessionId: session.id };
    } catch (error: any) {
        console.error(error);
        return { error: error.message };
    }
});
