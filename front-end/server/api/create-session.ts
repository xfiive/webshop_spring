import Stripe from 'stripe';

export default defineEventHandler(async (event) => {
    const config = useRuntimeConfig();

    const stripe = new Stripe(config.stripeSecretKey, {
        apiVersion: '2024-06-20',
    });

    const body = await readBody(event);

    // Assuming body contains the Order array
    const orders: Order[] = body.orders;

    // Map your Order array to Stripe's line_items
    const lineItems = orders.map((order) => {
        // Calculate the total price for the order
        const basePrice = order.product.price;
        const additionalOptionsPrice = order.orderGroups.reduce((sum, group) => {
            return sum + group.options.reduce((optSum, option) => optSum + option.price, 0);
        }, 0);
        const unitPrice = basePrice + additionalOptionsPrice;

        // Build the product name, including options if needed
        let productName = order.product.productName;
        const selectedOptions = order.orderGroups.flatMap((group) =>
            group.options.map((option) => option.name)
        );
        if (selectedOptions.length > 0) {
            productName += ` (${selectedOptions.join(', ')})`;
        }

        return {
            price_data: {
                currency: 'usd', // Adjust the currency as needed
                product_data: {
                    name: productName,
                },
                unit_amount: Math.round(unitPrice * 100), // Convert to cents
            },
            quantity: order.count,
        };
    });

    try {
        const session = await stripe.checkout.sessions.create({
            payment_method_types: ['card'],
            line_items: lineItems,
            mode: 'payment',
            success_url: `${event.node.req.headers.origin}/checkout/success?session_id={CHECKOUT_SESSION_ID}`,
            cancel_url: `${event.node.req.headers.origin}/checkout/cancel`,
        });

        return { sessionId: session.id };
    } catch (error: any) {
        console.error(error);
        return { error: error.message };
    }
});
