<template>
    <div class="border p-4 rounded shadow relative" v-if="product">
        <img :src="product.productImage" alt="Product Image" class="w-full h-48 object-cover mb-4">
        <h2 class="text-xl font-semibold mb-2">{{ product.productName }}</h2>
        <p class="text-lg mb-4">${{ product.price.toFixed(2) }}</p>
        <!-- Display product description if available -->
        <p v-if="product.productDescription" class="mb-4">{{ product.productDescription }}</p>
        <p v-else class="mb-4 text-gray-500">No description available.</p>
    </div>
</template>

<script setup lang="ts">
import type { Product } from '~/models/product';

defineProps<{ product: Product }>();

// const emit = defineEmits(['edit', 'delete', 'propCreate', 'propView']);

</script>
