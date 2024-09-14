<template>
    <div class="flex items-center p-4 border-b border-gray-200">
        <!-- Product Image -->
        <img
            :src="order.product.productImage"
            alt="Product Image"
            class="w-16 h-16 object-cover mr-4"
        />

        <!-- Product Details -->
        <div class="flex-1">
            <!-- Product Name and Delete Button -->
            <div class="flex justify-between items-center">
                <h2 class="text-lg font-semibold">
                    {{ order.product.productName }}
                </h2>
                <!-- Delete Button -->
                <button @click="handleDelete" class="text-red-500 hover:text-red-700">
                    &times;
                </button>
            </div>

            <!-- Price and Quantity -->
            <div class="flex items-center mt-2">
                <p class="text-gray-700">Price: ${{ totalPrice }}</p>
                <!-- Quantity Input -->
                <div class="flex items-center ml-4">
                    <label class="mr-2">Qty:</label>
                    <input
                        type="number"
                        v-model.number="count"
                        @change="handleCountChange"
                        min="1"
                        class="w-16 border rounded px-2 py-1"
                    />
                </div>
            </div>

            <!-- Edit Button -->
            <button @click="handleEdit" class="mt-2 text-blue-500 hover:text-blue-700">
                Edit
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue';
import type { Order } from '~/models/order';

const props = defineProps<{ order: Order }>();

const emits = defineEmits(['update:count', 'edit', 'delete']);

const count = ref(props.order.count);

/**
 * Watch for changes in the order's count prop to keep local count in sync.
 */
watch(
    () => props.order.count,
    (newCount) => {
        count.value = newCount;
    }
);

/**
 * Computes the total price of the order, including additional options and quantity.
 */
const totalPrice = computed(() => {
    const basePrice = props.order.product.price;
    const additionalOptionsPrice = props.order.orderGroups.reduce((sum, group) => {
        return (
            sum +
            group.options.reduce((optSum, option) => optSum + option.price, 0)
        );
    }, 0);
    return ((basePrice + additionalOptionsPrice) * count.value).toFixed(2);
});

/**
 * Emits an event when the quantity changes.
 */
function handleCountChange() {
    emits('update:count', count.value);
}

/**
 * Emits an event to edit the order.
 */
function handleEdit() {
    emits('edit', props.order);
}

/**
 * Emits an event to delete the order.
 */
function handleDelete() {
    emits('delete', props.order);
}
</script>

<style scoped>
/* Additional styles if needed */
</style>
