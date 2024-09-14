<script setup lang="ts">
import type { ProductOption, ProductOptionGroup } from '~/models/product';

const { group } = defineProps<{ group: ProductOptionGroup }>();

const prepareOrderStore = usePrepareOrderStore();

const selectedBuff = ref<ProductOption[]>([]);

const isOption = (option: ProductOption): boolean => selectedBuff.value.includes(option);

const handleOptionClick = (option: ProductOption) => {
    switch (group.availableOptionsState) {
        case 'MULTIPLE_OPTIONS_ALLOWED':
            if (isOption(option)) {
                selectedBuff.value.splice(selectedBuff.value.indexOf(option), 1);
            }
            else{
                selectedBuff.value.push(option);
            }
            break;
        case 'SINGLE_OPTION_ALLOWED':
            selectedBuff.value = [];
            selectedBuff.value.push(option);
            break;
    }

    prepareOrderStore.setTmpOrderData(group, [...selectedBuff.value]);
};

</script>

<template>
    <div class="mb-6">
        <div class="flex mb-2">
            <h2 class="text-2xl font-semibold">{{ group.name }}</h2>
            <h2 v-if="group.isRequired" class="text-red-600 font-extrabold">*</h2>
        </div>
        <div class="options grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <div v-for="option in group.productOptions"
                 @click="handleOptionClick(option)"
                 :class="isOption(option) ? 'selected-option' : '' "
                 class="p-4 border rounded-md text-center">
                <img :src="option.image" alt="Option Image" class="w-24 h-24 mx-auto mb-2" />
                <p class="font-medium">{{ option.name }}</p>
                <p class="font-bold"
                   :class="isOption(option) ? 'text-white' : 'text-green-600' "
                >${{ option.price.toFixed(2) }}</p>
            </div>
        </div>
    </div>
</template>

<style scoped>
.selected-option {
    @apply bg-green-600 transition-all
}
</style>