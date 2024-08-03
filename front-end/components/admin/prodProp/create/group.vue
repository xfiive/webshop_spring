<template>
    <div class="border p-4 rounded-md">
        <button @click="$emit('removeGroup')" class="float-right text-red-500 hover:text-red-700">❌</button>
        <div class="mb-4">
            <label for="groupName" class="block text-sm font-medium text-gray-700">Group Name</label>
            <input id="groupName" v-model="group.name" type="text" required
                   class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2">
        </div>

        <div class="flex items-center mb-4">
            <input id="isRequired" type="checkbox" v-model="group.isRequired">
            <label for="isRequired" class="ml-2 block text-sm text-gray-900">Is Required?</label>
        </div>

        <div class="mb-4">
            <label for="modificationMode" class="block text-sm font-medium text-gray-700">Modification Mode</label>
            <select id="modificationMode" v-model="group.groupModificationMode" required
                    class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2">
                <option value="" disabled>Select mode</option>
                <option value="modifiable">Modifiable</option>
                <option value="unmodifiable">Unmodifiable</option>
            </select>
        </div>

        <div class="mb-4">
            <label for="availableState" class="block text-sm font-medium text-gray-700">Available State</label>
            <select id="availableState" v-model="group.availableOptionsState" required
                    class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2">
                <option value="">None</option>
                <option value="SINGLE_OPTION_ALLOWED">Single Option</option>
                <option value="MULTIPLE_OPTIONS_ALLOWED">Multiple Option</option>
            </select>
        </div>

        <!-- Product Options Management -->
        <div>
            <h3 class="text-lg font-semibold">Product Options</h3>
            <div v-for="(option, index) in group.productOptions" :key="index" class="mb-3 i-group">
                <AdminProdPropCreateOption :option="option" @removeOption="removeOption(index)">
                </AdminProdPropCreateOption>
            </div>
            <button @click="addOption" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded">
                Add Option
            </button>
        </div>
    </div>
</template>

<script lang="ts" setup>

import type { ProductOptionGroup } from '~/models/product';

const { group } = defineProps<{ group: ProductOptionGroup }>();

const addOption = () => {
    group.productOptions.push({
        productOptionId: null,
        groupId: null,
        name: 'New Option',
        image: '',
        price: 0,
        accessible: true
    });
};

const removeOption = (index: number) => {
    group.productOptions.splice(index, 1);
};
</script>

<style scoped>
.i-group:nth-child(2n) {
    @apply bg-cyan-200;
}
.i-group:nth-child(2n+1) {
    @apply bg-lime-200;
}
</style>
