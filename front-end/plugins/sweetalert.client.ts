import Swal from 'sweetalert2'
import { defineNuxtPlugin } from '#app'

export default defineNuxtPlugin((nuxtApp) => {
    nuxtApp.provide('swal', Swal)
})
