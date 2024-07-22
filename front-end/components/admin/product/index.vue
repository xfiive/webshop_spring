<template>
    <div class="border p-4 rounded shadow relative" v-if="product">
        <!-- Product ID in the upper left corner -->
        <div class="absolute top-2 left-2 bg-gray-200 px-2 py-1 rounded-tr rounded-bl text-sm">
            ID: {{ product.productId }}
        </div>
        <!-- Edit and Delete buttons in the upper right corner -->
        <div class="absolute top-2 right-2 space-x-2">
            <button @click="editProduct(product.productId)" class="text-blue-500 hover:text-blue-700">
                📝
            </button>
            <button @click="deleteProduct(product.productId)" class="text-red-500 hover:text-red-700">
                ❌
            </button>
        </div>
        <img :src="product.productImage" alt="Product Image" class="w-full h-48 object-cover mb-4">
        <h2 class="text-xl font-semibold mb-2">{{ product.productName }}</h2>
        <p class="text-md">Properties ID:
            <b v-if="product.productPropertiesId">{{ product.productPropertiesId }}</b>
            <button v-else @click="createProps(product.productId)" class="text-blue-500 hover:text-green-700">
                Create
            </button>
        </p>
        <p class="text-lg mb-4">${{ product.price.toFixed(2) }}</p>
        <!-- Display product description if available -->
        <p v-if="product.productDescription" class="mb-4">{{ product.productDescription }}</p>
        <p v-else class="mb-4 text-gray-500">No description available.</p>
    </div>
</template>

<script setup lang="ts">
import type { Product } from '~/models/product';

defineProps<{ product: Product }>();

const emit = defineEmits(['edit', 'delete', 'propCreate']);

const editProduct = (productId: number) => {
    emit('edit', productId);
};

const deleteProduct = (productId: number) => {
    emit('delete', productId);
};

const createProps = (productId: number) => {
    emit('propCreate', productId);
};

</script>
