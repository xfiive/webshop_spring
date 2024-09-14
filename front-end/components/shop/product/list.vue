<template>
    <div class="container mx-auto p-4">
        <div v-if="products.length > 0" class="grid gap-4 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3">
            <ShopProduct v-for="product in products" :product @click="handleProduct(product)" />
        </div>
        <p v-else class="text-center">No products available.</p>
    </div>
    <ModalWide :show="modalOrder" @close="modalOrder = false">
        <template #body>
            <ShopProductOrder v-if="productProps" :product-properties="productProps" />
        </template>
        <template #bottom>
            <div class="flex content-center">
                <button @click="handleOrder" class="text-lg rounded-2xl bg-green-400 p-4 m-auto">TO CART</button>
            </div>
        </template>
    </ModalWide>
</template>

<script setup lang="ts">
import type { Product, ProductProperties } from '~/models/product';

const defaultProductProps: ProductProperties = {
    product: null,
    productPropertiesId: null,
    productOptionGroups: [],
    description: 'PRODUCT PROPERTIES ID WAS NOT ASSIGNED'
};

const api = useApi();
const prepareOrderStore = usePrepareOrderStore();

const products = ref<Product[]>([]);
const productProps = ref<ProductProperties>(defaultProductProps);

const fetchProducts = async () => products.value = await api.product.getAll() || [];
watch(products, async () => {
    if (products.value.length === 0) {
        await fetchProducts();
    }
});
onMounted(fetchProducts);

const modalOrder = ref(false);

const handleProduct = async (product: Product) => {
    productProps.value = product.productPropertiesId == null ? defaultProductProps :
        await api.prodProp.prop.getById(product.productPropertiesId);
    modalOrder.value = true;
    prepareOrderStore.resetTmpOrderData();
};

const handleOrder = () => {
    if (prepareOrderStore.isValidateOrder(productProps.value))
    {
        modalOrder.value = false;
        prepareOrderStore.pushOrderFromTmp(productProps.value);
    }
};

</script>
