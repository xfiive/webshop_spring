<template>
    <div class="container mx-auto p-4">
        <div v-if="products.length > 0" class="grid gap-4 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3">
            <ShopProduct v-for="product in products" :product="product" @click="handleProduct(product.productPropertiesId)" />
        </div>
        <p v-else class="text-center">No products available.</p>
    </div>
    <ModalWide :show="modalOrder" @close="modalOrder = false">
        <template #body>
            <ShopProductOrder v-if="productProps" :product-properties="productProps" />
        </template>
    </ModalWide>
</template>

<script setup lang="ts">
import type { Product, ProductProperties } from '~/models/product';

const api = useApi();

const products = ref<Product[]>([]);
const productProps = ref<ProductProperties>();

const fetchProducts = async () => products.value = await api.product.getAll() || [];
watch(products, async () => {
    if (products.value.length === 0) {
        await fetchProducts();
    }
});
onMounted(fetchProducts);

const modalOrder = ref(false);

const handleProduct = async (propsId: number) => {
    productProps.value = await api.prodProp.prop.getById(propsId);
    modalOrder.value = true;
}

</script>
