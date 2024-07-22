<template>
  <transition name="modal">
    <div class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50" v-if="show">
      <div class="bg-white rounded-lg shadow-lg max-w-md w-full mx-2 p-4">
        <div class="border-b pb-2 mb-4">
          <slot name="header">
            Default Header
          </slot>
        </div>
        <div class="mb-4">
          <slot name="body">
            Default Body
          </slot>
        </div>
        <div class="border-t pt-2 mt-4 text-right">
          <slot name="footer">
            Default Footer
            <button class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700" @click="$emit('close')">
              OK
            </button>
          </slot>
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup>
const props = defineProps({
  show: {
    type: Boolean,
    required: true
  }
});
</script>

<style scoped>
.modal-enter {
  opacity: 0;
}

.modal-enter-active {
  opacity: 1;
  transition: opacity 0.3s ease;
}

.modal-leave-active {
  opacity: 0;
  transition: opacity 0.3s ease;
}
</style>
