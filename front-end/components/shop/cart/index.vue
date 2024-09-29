<script setup lang="ts">
import type { Order } from '~/models/order';
import type { ProductProperties } from '~/models/product';

const orderStore = useOrderStore();
const prepareOrderStore = usePrepareOrderStore();

const { orders } = storeToRefs(orderStore);

function updateOrderCount(index: number, newCount: number) {
    orderStore.updateOrderCount(index, newCount);
}

function editOrder(order: Order, index: number) {
    prepareOrderStore.pushOrderToTmp(order);
    productProps.value = order.refProdProp;
    console.log(`Ref is ${order.refProdProp === undefined ? 'undefined' : 'Defined'}`);
    modalOrderEdit.value = true;
    orderEditIdx.value = index;
}

function deleteOrder(index: number) {
    orderStore.removeOrder(index);
}

const modalOrderEdit = ref<boolean>(false);

const productProps = ref<ProductProperties | undefined>();

const orderEditIdx = ref<number | undefined>();

function handleOrderEdit() {
    if (orderEditIdx.value != undefined && (productProps.value && prepareOrderStore.isValidateOrder(productProps.value))) {
        modalOrderEdit.value = false;
        prepareOrderStore.pushUpdatedOrderFromTmp(orderEditIdx.value, productProps.value);
    }
}

function handleModalOrderEditClose() {
    modalOrderEdit.value = false;
    prepareOrderStore.resetTmpOrderData();
}

</script>

<template>
    <div class="grid gap-4 grid-cols-1">
        <ShopCartOrder v-if="orders.length" v-for="(order, index) in orders" :order
                       @update:count="(newCount) => updateOrderCount(index, newCount)"
                       @edit="() => editOrder(order, index)"
                       @delete="() => deleteOrder(index)" />
        <p v-else class="text-lg text-gray-500">It is empty</p>
    </div>

    <ModalWide :show="modalOrderEdit" @close="handleModalOrderEditClose">
        <template #body>
            <ShopProductOrder v-if="productProps" :product-properties="productProps" />
            <p v-else class="text-lg text-red-600">Something is wrong...</p>
        </template>
        <template #bottom>
            <div class="flex content-center">
                <button @click="handleOrderEdit" class="text-lg rounded-2xl bg-green-400 p-4 m-auto">Edit</button>
            </div>
        </template>
    </ModalWide>
</template>

<style scoped>

</style>