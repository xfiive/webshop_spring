<template>
    <div class="flex flex-col min-h-screen">
        <header class="shadow-sm bg-white">
            <nav class="container mx-auto p-4 flex justify-between items-center">
                <NuxtLink to="/admin" class="font-bold text-xl">Admin Home</NuxtLink>
                <ul class="flex gap-4">
                    <li>
                        <NuxtLink to="/admin/products" class="text-lg">Products</NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/clients" class="text-lg">Clients</NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/delivery" class="text-lg">Delivery</NuxtLink>
                    </li>
                </ul>
                <ul class="flex gap-4">
                    <li v-if="!authenticated" class="text-lg">
                        <NuxtLink to="/login">Login</NuxtLink>
                    </li>
                    <li v-if="authenticated" class="text-lg">
                        <a @click="logoutClick" class="cursor-pointer">Logout</a>
                    </li>
                </ul>
            </nav>
        </header>
        <div class="container mx-auto p-4 flex-grow">
            <slot />
        </div>
        <footer class="bg-gray-800 text-white py-4">
            <div class="container mx-auto text-center">
                <h1 class="text-xl">Rat-band ©, all rights are reserved</h1>
            </div>
        </footer>
    </div>
</template>

<script lang="ts" setup>

const router = useRouter();

const authStore = useAuthStore();

const { authenticated } = storeToRefs(authStore);

const logoutClick = () => {
    authStore.logout();
    router.push('/login');
};
</script>

<style scoped>
.router-link-exact-active {
    color: crimson;
}
</style>
