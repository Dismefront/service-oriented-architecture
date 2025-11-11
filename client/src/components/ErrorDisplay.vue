<template>
  <div v-if="error" class="error-display">
    <h3>Error</h3>
    <p>{{ error }}</p>
    <button @click="clearError">Close</button>
  </div>
</template>

<script>
export default {
  name: 'ErrorDisplay',
  props: {
    error: {
      type: String,
      default: null
    }
  },
  emits: ['clear'],
  methods: {
    clearError() {
      this.$emit('clear');
    }
  }
};
</script>

<style scoped>
.error-display {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
  border-radius: 4px;
  padding: 15px;
  margin: 10px 0;
}

.error-display h3 {
  margin-top: 0;
}

.error-display button {
  margin-top: 10px;
  padding: 5px 10px;
  background-color: #721c24;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}
</style>