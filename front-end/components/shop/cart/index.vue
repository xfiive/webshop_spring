<script setup lang="ts">
const orderStore = useOrderStore();

const { orders } = storeToRefs(orderStore);

</script>

<template>
    <div class="grid gap-4 grid-cols-1">
        <ShopCartOrder v-for="order in orders" :order/>
    </div>
</template>

<style scoped>

</style>