<template>
    <div class="mt-7 max-w-sm mx-auto text-center bg-slate-300">
        <p class="mt-7 text-7x1 font-bold">{{ error.statusCode }}</p>
        <p class="mt-7 text-6x1">What a shame</p>
        <p class="mt-7">{{ error.message }}</p>
        <button class="btn my-7" @click="handleClearError">Go Home</button>
    </div>
</template>

<script setup>
defineProps(['error']);

function handleClearError() {
    clearError({ redirect: '/' });
}
</script>

<style scoped></style>