<template>
    <div>
        <div class="container mx-auto p-4">
            <div class="flex justify-between items-center mb-4">
                <h1 class="text-2xl font-bold">Product List</h1>
            </div>
            <div v-if="products" class="grid gap-4 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3">
                <div v-for="product in products" :key="product.productId" class="border p-4 rounded shadow"
                     @click="showProduct(product)">
                    <img alt="Product Image" class="w-full h-48 object-cover mb-4">
                    <h2 class="text-xl font-semibold mb-2">{{ product.productName }}</h2>
                    <p class="text-md mb-2">ID: {{ product.productId }}</p>
                    <p class="text-lg mb-4">${{ product.price.toFixed(2) }}</p>
                </div>
            </div>
            <p v-else class="text-center">No products available.</p>
        </div>
<!--        <Modal :show="modalAdd" @close="modalAdd = false">
            <template #header>
                <h3 class="text-xl font-bold">Add Product</h3>
            </template>
            <template #body v-if="selectedProduct?.productProperties">
                <ProductPropertiesView :productProperties="selectedProduct?.productProperties"
                                       @submit-configuration="handleProductSubmit" />
            </template>
            <template #footer>
                <button class="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-700"
                        @click="modalAdd = false">Close
                </button>
            </template>
        </Modal>
        -->
    </div>
</template>

<script setup lang="ts">

import type { Product } from '~/models/product';
import product from '~/repository/modules/product';
import { useApi } from '~/composables/useApi';

const api = useApi();

const products = ref<Product[] | null>(null);

const fetchProducts = async () => {
    products.value = await api.product.getAll();
    console.log("HELLO WORLD")
};

onMounted(() => fetchProducts())

watch(products, async (val) => {
    if (val === null) await fetchProducts();
});

</script>

<style scoped>
/* Add any custom styles if needed */
</style>