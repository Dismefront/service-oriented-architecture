<script setup lang="ts">
import NavbarView from '@/components/NavbarView.vue';
import PageSizeView from '@/components/PageSizeView.vue';
import RoutesDataView from '@/components/RoutesDataView.vue';
import SearchInputsView from '@/components/SearchInputsView.vue';
import SpecialActions from '@/components/SpecialActions.vue';
</script>

<template>
  <NavbarView />

  <section class="content">
    <SearchInputsView />
    <RoutesDataView />
    <PageSizeView />
    <SpecialActions />
  </section>
</template>

<style scoped>
.content {
  padding: 1.5rem;
  font-family: Arial, sans-serif;
}
</style>
