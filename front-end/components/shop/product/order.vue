<template>
    <div class="product-properties p-6 bg-white shadow-md rounded-lg">
        <div class="product-header text-center">
            <h1 class="text-3xl font-bold mb-4">{{ productProperties.product.productName }}</h1>
            <img :src="productProperties.product.productImage" alt="Product Image" class="w-48 h-48 mx-auto mb-4" />
            <p class="text-gray-600 mb-4">{{ productProperties.product.productDescription }}</p>
            <p class="text-green-600 font-bold text-xl">Price: ${{ productProperties.product.price.toFixed(2) }}</p>
        </div>
        <div class="option-groups mt-8">
            <div v-for="group in productProperties.productOptionGroups" :key="group.productOptionGroupId" class="mb-6">
                <h2 class="text-2xl font-semibold mb-2">{{ group.name }}</h2>
                <div class="options grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                    <div v-for="option in group.productOptions" :key="option.productOptionId"
                         class="option p-4 border rounded-md text-center">
                        <img :src="option.image" alt="Option Image" class="w-24 h-24 mx-auto mb-2" />
                        <p class="font-medium">{{ option.name }}</p>
                        <p class="text-green-600 font-bold">${{ option.price.toFixed(2) }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>

import type { Product, ProductProperties } from '~/models/product';

defineProps<{ productProperties: ProductProperties }>();
</script>

<style scoped>
.product-properties {
    font-family: Arial, sans-serif;
}
</style>
