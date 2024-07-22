<template>
	<NuxtLink class="base-button" :class="{ inverse: inverse }">
		<slot>Order now</slot>
	</NuxtLink>
</template>

<script>
export default {
	name: "base-button",
	props: {
		inverse: {
			type: Boolean,
			default: false,
		},
	},
};
</script>

<style scoped>  
    .base-button{
        display: block;
        cursor: pointer;
        text-transform: uppercase;
        padding: 15px 20px;
        color: #FFFFFF;
        font-size: 16px;
        background-color: #EF3F75;
        border-radius: 30px;
        border: none;
    }
    .inverse{
        color: #EF3F75;
        background-color: #FFFFFF;
    }
</style>