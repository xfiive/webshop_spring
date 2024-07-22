<template>
    <div class="container mx-auto p-4">
        <div class="flex justify-between items-center mb-4">
            <h1 class="text-2xl font-bold">Product List</h1>
            <button class="text-lg btn" @click="addProduct">Add</button>
        </div>
        <div v-if="products.length > 0" class="grid gap-4 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3">
            <AdminProduct v-for="product in products" :key="product.productId" :product="product"
                          @edit="editProduct"
                          @delete="deleteProduct"
                          @propCreate="createProp" />
        </div>
        <p v-else class="text-center">No products available.</p>
    </div>
</template>

<script setup lang="ts">
import type { Product } from '~/models/product';

const props = defineProps<{
    products: Product[]
}>();

const emit = defineEmits(['edit', 'delete', 'add', 'propCreate']);

const editProduct = (productId: number) => {
    emit('edit', productId);
};

const deleteProduct = (productId: number) => {
    emit('delete', productId);
};

const addProduct = () => {
    emit('add');
};

const createProp = (productId: number) => {
    emit('propCreate', productId);
};

</script>

<style scoped>
/* Add any custom styles if needed */
</style>
