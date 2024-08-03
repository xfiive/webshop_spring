<template>
    <form @submit.prevent="submitForm">

        <div v-for="(group, index) in productOptionGroups" :key="index" class="mb-6 i-group">
            <AdminProdPropCreateGroup :group="group"
                                      @removeGroup="removeGroup(index)">
            </AdminProdPropCreateGroup>
        </div>

        <button type="button" @click="addGroup"
                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
            Add Group
        </button>

        <!--        <div class="flex items-center border border-gray-300 rounded-lg overflow-hidden">
                    <input type="text" placeholder="John Smith" class="px-2 py-2 w-full focus:outline-none" />
                    <div class="flex self-stretch px-3 border-l border-gray-300 bg-green-400">
                        <button>Sort</button>
                    </div>
                </div>-->

        <div class="mt-8">
            <button type="submit" @click="$emit('onSubmit', productOptionGroups)"
                    class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                Save Product Properties
            </button>
        </div>
    </form>
</template>

<script lang="ts" setup>

import type { ProductOptionGroup } from '~/models/product';

const productOptionGroups = ref<ProductOptionGroup[]>([]);

const addGroup = () => {
    productOptionGroups.value.push({
        name: '',
        isRequired: false,
        groupModificationMode: '',
        availableOptionsState: '',
        productOptions: [],
        productOptionGroupId: null,
        productPropertiesId: null
    });
};

const removeGroup = (index: number) => {
    productOptionGroups.value.splice(index, 1);
};

const submitForm = () => {
};
</script>

<style scoped>
.i-group:nth-child(2n) {
    @apply bg-blue-200;
}

.i-group:nth-child(2n+1) {
    @apply bg-green-200;
}
</style>
