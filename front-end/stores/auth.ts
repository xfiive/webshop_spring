import { defineStore } from 'pinia';
import { useApi } from '~/composables/useApi';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        authenticated: typeof useCookie('token')?.value === 'string',
        loading: false,
    }),
    actions: {
        async login(username: string, password: string) {
            const api = useApi();
            this.loading = true;
            const token = await api.auth.login({ username, password });
            this.loading = false;

            console.log(`Token received: ${token}`);

            if (token) {
                const cookie = useCookie('token');
                cookie.value = token;
                this.authenticated = true;
            }
        },

        logout() {
            const cookie = useCookie('token');
            cookie.value = null;
            this.authenticated = false;
        }
    },
    getters:{
      getToken(): string | null {
          return useCookie('token').value || null;
      }
    },
});
