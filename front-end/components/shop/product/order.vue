<template>
    <div class="product-properties p-6">
        <div class="product-header text-center" v-if="productProperties.product">
            <h1 class="text-3xl font-bold mb-4">{{ productProperties.product.productName }}</h1>
            <img :src="productProperties.product.productImage" alt="Product Image" class="w-48 h-48 mx-auto mb-4" />
            <p class="text-gray-600 mb-4">{{ productProperties.product.productDescription }}</p>
            <p class="text-green-600 font-bold text-xl">Price: ${{ totalPrice }}</p>
        </div>
        <h2 v-else>No Product assigned</h2>
        <div class="option-groups mt-8">
            <ShopProductGroup v-for="group in productProperties.productOptionGroups" :group />
        </div>
    </div>
</template>

<script lang="ts" setup>

import type { ProductProperties } from '~/models/product';

const { productProperties } = defineProps<{ productProperties: ProductProperties }>();

// defineEmits(['onSubmit']);

const {getAdditionalCost} = storeToRefs(usePrepareOrderStore());

const totalPrice = computed(() => {
    const basePrice = productProperties.product?.price || 0;
    return (basePrice + getAdditionalCost.value).toFixed(2);
});


const checkTmpData = (): boolean => {
    return false;
};

const handleSumbit = () => {
    if (checkTmpData()){
    }
};

</script>

<style scoped>
.product-properties {
    font-family: Arial, sans-serif;
}
</style>
