<template>
    <div
        class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50"
        v-if="show"
        @click="closeOnBackgroundClick"
    >
        <div
            class="bg-white rounded-lg shadow-lg max-w-screen-md w-full mx-2 relative custom-scrollbar"
            @click.stop
        >
            <!-- Close Button -->
            <button
                class="absolute top-2 right-2 text-xl"
                @click="$emit('close')"
                aria-label="Close modal"
            >
                ✖
            </button>

            <!-- Modal Content with Transition -->
            <transition name="modal">
                <div
                    class="p-4 overflow-y-auto max-h-[80vh]"
                >
                    <!-- Modal Title -->
                    <h2 class="text-xl font-bold mb-4">
                        <slot name="title">Default Title</slot>
                    </h2>

                    <!-- Modal Body -->
                    <div>
                        <slot name="body">
                            Default Body
                        </slot>
                    </div>
                </div>
            </transition>
            <slot name="bottom">
            </slot>
        </div>
    </div>
</template>

<script setup>

defineProps({
    show: {
        type: Boolean,
        required: true,
    },
});

const emit = defineEmits(['close']);

function closeOnBackgroundClick() {
    emit('close');
}
</script>

<style scoped>
/* You can add custom styles here if needed */
</style>
