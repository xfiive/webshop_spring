<template>
  <div class="flex flex-col min-h-screen">
    <Header></Header>
    <div class="flex-grow">
      <div class="container mx-auto py-8">
        <div style="height: 100px;"> </div>
          <slot />
        </div>
      </div>
      <Footer></Footer>
    </div>
</template>

<script lang="ts" setup>
/* import { storeToRefs } from 'pinia';
import { useAuthStore } from '~/stores/auth';

const router = useRouter();

const { logUserOut } = useAuthStore();
const { authenticated } = storeToRefs(useAuthStore()); // make authenticated state reactive

const logout = () => {
  logUserOut();
  router.push('/login');
}; */
</script>
