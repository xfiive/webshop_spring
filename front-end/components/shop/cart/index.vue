<script setup lang="ts">
import type { Order } from '~/models/order';

const orderStore = useOrderStore();

const { orders } = storeToRefs(orderStore);

function updateOrderCount(index: number, newCount: number) {
    orderStore.updateOrderCount(index, newCount);
}

function editOrder(order: Order) {
    // Logic to edit the order
}

function deleteOrder(index: number) {
    orderStore.removeOrder(index);
}


</script>

<template>
    <div class="grid gap-4 grid-cols-1">
        <ShopCartOrder v-for="(order, index) in orders" :order
                       @update:count="(newCount) => updateOrderCount(index, newCount)"
                       @edit="(order) => editOrder(order)"
                       @delete="(order) => deleteOrder(index)" />
    </div>
</template>

<style scoped>

</style>