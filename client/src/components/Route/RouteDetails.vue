<template>
  <div class="route-details" v-if="route">
    <h2>Route Details</h2>

    <div class="detail-item">
      <strong>ID:</strong>
      <span>{{ route.id }}</span>
    </div>

    <div class="detail-item">
      <strong>Name:</strong>
      <span>{{ route.name }}</span>
    </div>

    <div class="detail-item">
      <strong>Distance:</strong>
      <span>{{ route.distance }}</span>
    </div>

    <div class="detail-item">
      <strong>Creation Date:</strong>
      <span>{{ formatDate(route.creationDate) }}</span>
    </div>

    <div class="detail-item">
      <strong>Coordinates:</strong>
      <span>X: {{ route.coordinates.x }}, Y: {{ route.coordinates.y }}</span>
    </div>

    <div class="detail-item" v-if="route.from">
      <strong>From Location:</strong>
      <span>X: {{ route.from.x }}, Y: {{ route.from.y }}, Z: {{ route.from.z }}</span>
    </div>

    <div class="detail-item">
      <strong>To Location:</strong>
      <span>X: {{ route.to.x }}, Y: {{ route.to.y }}, Z: {{ route.to.z }}</span>
    </div>

    <div class="actions">
      <button @click="onEdit">Edit</button>
      <button @click="onDelete">Delete</button>
      <button @click="onClose">Close</button>
    </div>
  </div>

  <div v-else class="no-route">No route selected</div>
</template>

<script lang="ts">
export default {
  name: 'RouteDetails',
  props: {
    route: {
      type: Object,
      default: null,
    },
  },
  emits: ['edit', 'delete', 'close'],
  methods: {
    formatDate(dateString) {
      if (!dateString) return 'N/A'
      return new Date(dateString).toLocaleString()
    },
    onEdit() {
      this.$emit('edit', this.route)
    },
    onDelete() {
      this.$emit('delete', this.route)
    },
    onClose() {
      this.$emit('close')
    },
  },
}
</script>

<style scoped>
.route-details {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #f9f9f9;
}

.detail-item {
  margin-bottom: 10px;
  display: flex;
}

.detail-item strong {
  width: 150px;
  display: inline-block;
}

.actions {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

.actions button {
  padding: 5px 15px;
}

.no-route {
  text-align: center;
  padding: 20px;
  font-style: italic;
  color: #666;
}
</style>
