<script setup lang="ts">

import type { ProductOption } from '~/models/product';

const { option } = defineProps<{ option: ProductOption }>();

</script>

<template>
    <div class="flex flex-col p-4 border rounded relative">
        <button @click="$emit('removeOption')" class="absolute top-2 right-2 text-red-500 hover:text-red-700">❌</button>
        <div class="flex items-center justify-between mb-2">
            <div class="mb-2">
                <label for="name" class="block text-sm font-medium text-gray-700">Name</label>
                <input type="text" id="name" v-model.number="option.name" required
                       class="mt-1 block w-full p-2 border border-gray-300 rounded shadow-sm">
            </div>
        </div>
        <div class="mb-2">
            <label for="image" class="block text-sm font-medium text-gray-700">Image (Base64)</label>
            <textarea id="image" v-model="option.image" rows="2"
                      class="mt-1 block w-full p-2 border border-gray-300 rounded shadow-sm"></textarea>
        </div>
        <div class="mb-2">
            <label for="price" class="block text-sm font-medium text-gray-700">Price</label>
            <input type="number" id="price" v-model.number="option.price"
                   class="mt-1 block w-full p-2 border border-gray-300 rounded shadow-sm">
        </div>
        <div class="mb-2">
            <label for="accessible" class="flex items-center">
                <input type="checkbox" id="accessible" v-model="option.accessible"
                       class="mr-2">
                Accessible
            </label>
        </div>
    </div>
</template>

<style scoped>

</style>