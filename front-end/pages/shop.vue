<template>
    <div class="container">
        <ShopProductList/>
    </div>
</template>

<script setup lang="ts">

const checkoutStatus = useState('checkoutStatus')

onMounted(()=>{
    console.log(checkoutStatus.value);
    if (checkoutStatus.value)
    useNuxtApp().$swal.fire(checkoutStatus.value);
})

</script>
