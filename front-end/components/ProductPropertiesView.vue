<template>
  <div class="p-4 bg-white shadow-md rounded-lg">
    <h2 class="text-2xl font-bold mb-4">Product Properties</h2>
    <div v-for="group in productProperties.optionGroups" :key="group.id" class="option-group mb-6">
      <h3 class="text-xl font-semibold mb-2">{{ group.name }} {{ (group.required ? `(Required)` : '') }}</h3>
      <div v-if="group.enableMode === EnableMode.Radio">
        <label v-for="option in group.options" :key="option.id" class="block mb-2">
          <input type="radio" :name="group.id" :value="option.id" @change="selectOption(group, option)" class="mr-2" />
          {{ option.name }} {{ option.price ? `($${option.price.toFixed(2)})` : '' }}
        </label>
      </div>
      <div v-else-if="group.enableMode === EnableMode.Checkbox">
        <label v-for="option in group.options" :key="option.id" class="block mb-2">
          <input type="checkbox" :value="option.id" @change="selectOption(group, option)" class="mr-2" />
          {{ option.name }} {{ option.price ? `(+$${option.price.toFixed(2)})` : '' }}
        </label>
      </div>
      <div v-else>
        <h1>Not implemented</h1>
      </div>
    </div>
    <button :disabled="buttonDisabled" class="text-white py-2 px-4 rounded" @click="submitConfiguration" type="button"
      :class="buttonDisabled ? 'bg-blue-200' : 'bg-blue-500'">Submit (${{ finalPrice }})</button>
  </div>
</template>
<!--

<script lang="ts">
import { defineComponent } from 'vue';
import { EnableMode, ProductProperties, ProductOptionGroup, ProductOption, ProductConfiguration } from '~/models/ProductModels';

export default defineComponent({
  name: 'ProductPropertiesView',
  props: {
    productProperties: {
      type: Object as () => ProductProperties,
      required: true
    }
  },
  data() {
    return {
      selectedOptions: {},
      productConf: new ProductConfiguration(this.$props.productProperties),
      buttonDisabled: true,
      finalPrice: 0
    };
  },
  computed: {
    EnableMode() {
      return EnableMode;
    }
  },
  methods: {
    selectOption(group: ProductOptionGroup, option: ProductOption) {
      this.productConf.toggle(group, option);
      this.buttonDisabled = !this.productConf.isValid();
      this.finalPrice = this.productConf.getPrice();
    },
    submitConfiguration() {
      this.$emit('submit-configuration', this.productConf);
    }
  }
});
</script>
-->

<style scoped>
.option-group {
  @apply mb-6;
}

.option {
  @apply block mb-2;
}
</style>
