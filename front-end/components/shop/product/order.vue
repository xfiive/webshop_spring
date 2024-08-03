<template>
    <div class="product-properties p-6">
        <div class="product-header text-center">
            <h1 class="text-3xl font-bold mb-4">{{ productProperties.product.productName }}</h1>
            <img :src="productProperties.product.productImage" alt="Product Image" class="w-48 h-48 mx-auto mb-4" />
            <p class="text-gray-600 mb-4">{{ productProperties.product.productDescription }}</p>
            <p class="text-green-600 font-bold text-xl">Price: ${{ productProperties.product.price.toFixed(2) }}</p>
        </div>
        <div class="option-groups mt-8">
            <ShopProductGroup v-for="group in productProperties.productOptionGroups" :group/>
        </div>
    </div>
</template>

<script lang="ts" setup>

import type { Product, ProductOption, ProductOptionGroup, ProductProperties } from '~/models/product';

defineProps<{ productProperties: ProductProperties }>();

const handleOptionClick = (group: ProductOptionGroup, option: ProductOption) => {

};

</script>

<style scoped>
.product-properties {
    font-family: Arial, sans-serif;
}
</style>
