<template>
  <div class="routes-view">
    <h1>Route Management</h1>
    
    <RouteList />
  </div>
</template>

<script>
import RouteList from '../../components/Route/RouteList.vue';

export default {
  name: 'RoutesView',
  components: {
    RouteList
  }
};
</script>

<style scoped>
.routes-view {
  padding: 20px;
}
</style>