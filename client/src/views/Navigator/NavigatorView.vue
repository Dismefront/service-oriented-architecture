<template>
  <div class="navigator-view">
    <h1>Navigator</h1>

    <Navigator />
  </div>
</template>

<script>
import Navigator from '../../components/Route/Navigator.vue'

export default {
  name: 'NavigatorView',
  components: {
    Navigator,
  },
}
</script>

<style scoped>
.navigator-view {
  padding: 20px;
}
</style>
