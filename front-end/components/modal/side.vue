<template>
    <transition name="modal">
        <div class="fixed inset-0 z-50 flex items-stretch justify-end bg-black bg-opacity-50" v-if="show">
            <div class="bg-white rounded-lg shadow-lg max-w-screen-md w-full m-2 p-4 relative overflow-y-auto">
                <button class="absolute top-2 right-2 text-xl" @click="$emit('close')">
                    ✖
                </button>
                <div >
                    <slot name="body">
                        Default Body
                    </slot>
                </div>
            </div>
        </div>
    </transition>
</template>

<script setup>
defineProps({
    show: {
        type: Boolean,
        required: true
    }
});

</script>

<style scoped>
</style>
