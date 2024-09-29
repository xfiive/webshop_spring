// middleware/checkout.global.ts
import {type SweetAlertArrayOptions} from 'sweetalert2';

export default defineNuxtRouteMiddleware((to) => {
    // Check if the user is redirected back from Stripe
    if (to.path === '/checkout/success') {
        // Store additional data using useState
        const checkoutStatus = useState('checkoutStatus', () => ({
            title: 'Success',
            text: 'Thank you for your purchase!',
            icon: 'success'
        }))

        return navigateTo('/shop', { replace: true })
    }

    if (to.path === '/checkout/cancel') {
        const checkoutStatus = useState('checkoutStatus', () => ({
            title: 'Failure',
            text: 'Purchase was cancelled!',
            icon: 'cancel'
        }))

        return navigateTo('/shop', { replace: true })
    }
})
