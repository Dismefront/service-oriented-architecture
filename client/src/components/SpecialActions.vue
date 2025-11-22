<script setup lang="ts">
import client from '@/api/client';
import { useRoutesStore } from '@/stores/routes';
import { ref } from 'vue';

const routesStore = useRoutesStore();
const uniqueDistances = ref<number[]>([]);
const deletedDistanceRef = ref<HTMLSelectElement>();

const updateUniqueDistances = () => {
  client.getUniqueDistances().then((data) => {
    uniqueDistances.value = data;
  });
};

const handleDeleteByDistance = () => {
  const distance = deletedDistanceRef.value?.value;
  if (!distance) {
    return;
  }

  client.deleteRoutesByDistance(Number.parseFloat(distance)).then(() => {
    routesStore.updateRoutes();
    updateUniqueDistances();
  });
};
</script>

<template>
  <div class="action-group">
    <h4>Unique Distance Values</h4>
    <button
      @click="updateUniqueDistances"
      type="button"
      class="btn-primary"
      title="Fetch unique distance values"
    >
      Get Unique Distances
    </button>
    <ul v-if="uniqueDistances.length > 0" class="unique-distances-list">
      <li v-for="dist in uniqueDistances" :key="dist">{{ dist.toFixed(2) }} km</li>
    </ul>
    <p v-else-if="uniqueDistances.length === 0" class="no-data">
      Click button to load unique distances
    </p>
  </div>

  <div class="action-group" v-if="uniqueDistances.length > 0">
    <h4>Delete Routes by Distance</h4>
    <div class="input-with-button">
      <select ref="deletedDistanceRef" class="select-distance" title="Select distance value">
        <option value="" disabled selected>Select a distance</option>
        <option v-for="dist in uniqueDistances" :key="dist" :value="dist">
          {{ dist.toFixed(2) }} km
        </option>
      </select>
      <button
        @click="handleDeleteByDistance"
        type="button"
        class="btn-danger"
        title="Delete all routes with this distance"
      >
        Delete Routes
      </button>
    </div>
  </div>
</template>

<style scoped>
.special-actions-panel {
  background: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 12px;
  padding: 1.5rem;
  margin-top: 1.5rem;
  max-width: 500px;
  font-size: 0.95rem;
  color: #333;
}

.special-actions-panel h3 {
  margin-top: 0;
  font-size: 1.25rem;
  color: #2c3e50;
  border-bottom: 2px solid #42b983;
  padding-bottom: 0.5rem;
}

.action-group {
  margin-bottom: 1.5rem;
}

.action-group h4 {
  margin-bottom: 0.75rem;
  color: #444;
  font-size: 1.1rem;
}

.btn-primary {
  background-color: #0062cc;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: background 0.2s;
}

.btn-primary:hover {
  background-color: #0051a8;
}

.btn-danger {
  background-color: #d32f2f;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: background 0.2s;
}

.btn-danger:hover {
  background-color: #b71c1c;
}

.unique-distances-list {
  list-style: none;
  padding: 0;
  margin-top: 0.5rem;
  display: flex;
}

.unique-distances-list li {
  background: #e8f5e8;
  border: 1px solid #c8e6c9;
  border-radius: 4px;
  padding: 0.4rem 0.75rem;
  margin-bottom: 0.4rem;
  font-size: 0.9rem;
  color: #1b5e20;
}

.no-data {
  font-style: italic;
  color: #888;
  margin-top: 0.5rem;
}

.input-with-button {
  display: flex;
  gap: 0.5rem;
  align-items: center;
  flex-wrap: wrap;
  width: 50%;
}

.select-distance {
  flex-grow: 1;
  min-width: 200px;
  padding: 0.5rem;
  border: 1px solid #aaa;
  border-radius: 6px;
  font-size: 0.95rem;
  background: white;
}

.select-distance:focus {
  outline: none;
  border-color: #42b983;
  box-shadow: 0 0 0 2px rgba(66, 185, 131, 0.2);
}

.warning-text {
  color: #d32f2f;
  font-size: 0.85rem;
  margin-top: 0.5rem;
  font-style: italic;
}
</style>
