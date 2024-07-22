<template>
    <div>
        <AdminProductList :products="productList" @edit="handleEdit" @delete="handleDelete" @add="handleAdd"
                          @propCreate="handlePropCreate" />
        <Modal :show="viewModalAdd" @close="viewModalAdd = false">
            <template #body>
                <form @submit.prevent="submitAddForm" class="max-w-lg mx-auto">
                    <div class="mb-4">
                        <label for="productName" class="block text-lg font-medium mb-2">Product Name</label>
                        <input type="text" id="productName" v-model="selectedProduct.productName"
                               class="w-full p-2 border border-gray-300 rounded" required />
                    </div>
                    <div class="mb-4">
                        <label for="price" class="block text-lg font-medium mb-2">Price</label>
                        <input type="number" id="price" v-model="selectedProduct.price"
                               class="w-full p-2 border border-gray-300 rounded" required />
                    </div>
                    <div class="mb-4">
                        <label for="productDescription" class="block text-lg font-medium mb-2">Product
                            Description</label>
                        <textarea id="productDescription" v-model="selectedProduct.productDescription"
                                  class="w-full p-2 border border-gray-300 rounded" rows="3"></textarea>
                    </div>
                    <div class="mb-4">
                        <label for="productImage" class="block text-lg font-medium mb-2">Product Image
                            (Base64)</label>
                        <textarea id="productImage" v-model="selectedProduct.productImage"
                                  class="w-full p-2 border border-gray-300 rounded" rows="4" required></textarea>
                    </div>
                    <button type="submit" class="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-700">Add
                        Product
                    </button>
                </form>
            </template>
        </Modal>
        <Modal :show="viewModalEdit" @close="viewModalEdit = false">
            <template #body>
                <form @submit.prevent="submitEditForm" class="max-w-lg mx-auto">
                    <div class="mb-4">
                        <label for="productId" class="block text-lg font-medium mb-2">Product ID</label>
                        <input type="text" id="productId" v-model="selectedProduct.productId"
                               class="w-full p-2 border border-gray-300 rounded" disabled />
                    </div>
                    <div class="mb-4">
                        <label for="productName" class="block text-lg font-medium mb-2">Product Name</label>
                        <input type="text" id="productName" v-model="selectedProduct.productName"
                               class="w-full p-2 border border-gray-300 rounded" required />
                    </div>
                    <div class="mb-4">
                        <label for="price" class="block text-lg font-medium mb-2">Price</label>
                        <input type="number" id="price" v-model="selectedProduct.price"
                               class="w-full p-2 border border-gray-300 rounded" required />
                    </div>
                    <div class="mb-4">
                        <label for="productDescription" class="block text-lg font-medium mb-2">Product
                            Description</label>
                        <textarea id="productDescription" v-model="selectedProduct.productDescription"
                                  class="w-full p-2 border border-gray-300 rounded" rows="3"></textarea>
                    </div>
                    <div class="mb-4">
                        <label for="productImage" class="block text-lg font-medium mb-2">Product Image
                            (Base64)</label>
                        <textarea id="productImage" v-model="selectedProduct.productImage"
                                  class="w-full p-2 border border-gray-300 rounded" rows="4" required></textarea>
                    </div>
                    <button type="submit" class="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-700">Edit
                        Product
                    </button>
                </form>
            </template>
        </Modal>
        <Modal :show="viewModalDel" @close="viewModalDel = false">
            <template #body>
                <div class="container mx-auto p-4">
                    <h1 class="text-2xl font-bold mb-4">Edit Product</h1>
                    <form @submit.prevent="submitDeleteForm" class="max-w-lg mx-auto">
                        <div class="mb-4">
                            <label for="productId" class="block text-lg font-medium mb-2">Product ID</label>
                            <input type="text" id="productId" v-model="selectedProduct.productId"
                                   class="w-full p-2 border border-gray-300 rounded" disabled />
                        </div>
                        <button type="submit" class="w-full bg-red-500 text-white py-2 rounded hover:bg-red-700">
                            Delete
                        </button>
                    </form>
                </div>
            </template>
        </Modal>
        <Modal :show="viewModalPropCreate" @close="viewModalPropCreate = false">
            <template #body>
                <AdminProdPropCreate @onSubmit="submitCreateProps" />
            </template>
        </Modal>
    </div>
</template>

<script lang="ts" setup>
import type { Product, ProductOptionGroup, ProductOption, GroupModificationMode } from '~/models/product';
import useApiService from '~/composables/useApiService';

definePageMeta({
    layout: 'admin'
});

const api = useApi();
const service = useApiService();

const selectedProduct = ref<Product>({} as Product);

const viewModalAdd = ref<boolean>(false);
const viewModalEdit = ref<boolean>(false);
const viewModalDel = ref<boolean>(false);
const viewModalPropCreate = ref(false);

const productList = ref<Product[]>([]);

const fetchProducts = async () => productList.value = await api.product.getAll() || [];

watch(productList, async () => {
    if (productList.value.length === 0) {
        await fetchProducts();
    }
});

onMounted(fetchProducts);


const findProduct = (productId: number) => selectedProduct.value = ({
    ...
        productList
            .value.find(p => p.productId === productId)
} || {}) as Product;

const handleAdd = () => {
    selectedProduct.value = {
        productName: '',
        price: 0,
        productImage: '',
        productDescription: '',
        productId: 0,
        productPropertiesId: 0
    } as Product;
    viewModalAdd.value = true;
};
const handleEdit = (productId: number) => {
    findProduct(productId);
    viewModalEdit.value = true;
};
const handleDelete = (productId: number) => {
    findProduct(productId);
    viewModalDel.value = true;
};
const handlePropCreate = (productId: number) => {
    findProduct(productId);
    viewModalPropCreate.value = true;
};

const submitAddForm = async () => {
    await api.product.add(selectedProduct.value);
    viewModalAdd.value = false;
    await fetchProducts();
};
const submitEditForm = async () => {
    await api.product.put(selectedProduct.value.productId, selectedProduct.value);
    viewModalEdit.value = false;
};
const submitDeleteForm = async () => {
    await api.product.deleteById(selectedProduct.value.productId);
    viewModalDel.value = false;
    await fetchProducts();
};
const submitCreateProps = async (groups: ProductOptionGroup[]) => {

    viewModalPropCreate.value = false;

    await service.prodProp.pushRaw({
        product: selectedProduct.value,
        productPropertiesId: 0,
        productOptionGroups: groups,
        description: ''
    });


    await fetchProducts();
};

</script>

<style></style>