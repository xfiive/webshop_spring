<template>
    <div class="flex items-center justify-center min-h-screen bg-gray-100">
        <div class="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-md">
            <div class="text-center">
                <h2 class="text-2xl font-semibold text-gray-800">Login</h2>
            </div>
            <div class="space-y-4">
                <div>
                    <label for="uname" class="block text-sm font-medium text-gray-700"><b>Username</b></label>
                    <input v-model="user.username" type="text" id="uname"
                           class="w-full px-3 py-2 mt-1 border border-gray-300 rounded focus:outline-none focus:ring focus:ring-indigo-100 focus:border-indigo-300"
                           placeholder="Enter Username" name="uname" required />
                </div>
                <div>
                    <label for="psw" class="block text-sm font-medium text-gray-700"><b>Password</b></label>
                    <input v-model="user.password" type="password" id="psw"
                           class="w-full px-3 py-2 mt-1 border border-gray-300 rounded focus:outline-none focus:ring focus:ring-indigo-100 focus:border-indigo-300"
                           placeholder="Enter Password" name="psw" required />
                </div>
                <div>
                    <button @click.prevent="loginClick"
                            class="w-full px-4 py-2 font-semibold text-white bg-indigo-600 rounded hover:bg-indigo-500 focus:outline-none focus:ring focus:ring-indigo-200">
                        Login
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useAuthStore } from '~/stores/auth';

const { login } = useAuthStore(); // use auth stores

const { authenticated, loading } = storeToRefs(useAuthStore()); // make authenticated state reactive

const user = ref({
    username: 'admin',
    password: 'admin'
});
const router = useRouter();

const loginClick = async () => {
    console.log(authenticated.value+"");
    await login(user.value.username, user.value.password);
};

watch(authenticated, (newValue, oldValue) => {
    if (newValue) {
        console.log(authenticated.value+"");
        router.push('/admin');
    }
});

watch(loading, (newValue, oldValue) => {console.log(`${newValue} ${oldValue} ${authenticated.value}`)});

</script>
