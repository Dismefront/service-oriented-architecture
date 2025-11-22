<script setup lang="ts">
import NavbarView from '@/components/NavbarView.vue';
import NavigatorDataView from '@/components/NavigatorDataView.vue';
import NavigatorInputsView from '@/components/NavigatorInputsView.vue';
</script>

<template>
  <NavbarView />

  <section class="content">
    <NavigatorInputsView />
    <NavigatorDataView />
  </section>
</template>

<style scoped>
.content {
  padding: 1.5rem;
  font-family: Arial, sans-serif;
}
</style>
