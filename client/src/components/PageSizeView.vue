<script setup lang="ts">
import { useRoutesStore } from '@/stores/routes';

const routesStore = useRoutesStore();
</script>

<template>
  <section class="page-info">
    <div class="page-info-field">
      <label for="pageNum">Page number</label>
      <input v-model.number="routesStore.page" id="pageNum" type="number" step="any" required />
    </div>
    <div class="page-info-field">
      <label for="pageSize">Page size</label>
      <input v-model.number="routesStore.size" id="pageSize" type="number" step="any" required />
    </div>
  </section>
</template>

<style scoped>
.page-info {
  display: flex;
  gap: 1rem;
}
.page-info-field {
  display: flex;
  flex-direction: column;
}
</style>
