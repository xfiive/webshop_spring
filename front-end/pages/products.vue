<template>
  <div>
    <h1>Products</h1>
    <div class="grid grid-cols-4 gap-5">
      <div v-for="p in products">
        <p>{{ p.productName }}</p>
        <img :src="`data:image/jpeg;base64,${p.productImage}`" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>

</script>

<style></style>