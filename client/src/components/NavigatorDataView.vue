<script setup lang="ts">
import { onMounted } from 'vue';
import { useNavigatorStore } from '@/stores/navigator';
import { parseExtendedIsoToHuman } from '@/helpers';

const navigatorsStore = useNavigatorStore();

onMounted(() => {
  navigatorsStore.updateRoutes();
});
</script>

<template>
  <table class="routes-table">
    <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Coordinates (X, Y)</th>
        <th>From (X, Y, Z)</th>
        <th>To (X, Y, Z)</th>
        <th>Distance</th>
        <th>Creation Date</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="route in navigatorsStore.routes" :key="route.id">
        <td>{{ route.id }}</td>
        <td>{{ route.name }}</td>
        <td>{{ route.coordinates.x }}, {{ route.coordinates.y }}</td>
        <td>{{ route.from.x }}, {{ route.from.y }}, {{ route.from.z }}</td>
        <td>{{ route.to.x }}, {{ route.to.y }}, {{ route.to.z }}</td>
        <td>{{ route.distance.toFixed(2) }} km</td>
        <td>{{ parseExtendedIsoToHuman((route as any).creationDate) }}</td>
      </tr>
    </tbody>
  </table>

  <p v-if="navigatorsStore.routes?.length === 0" class="no-results">No routes found.</p>
  <p v-if="navigatorsStore.routes === null" class="no-results">Loading...</p>
</template>

<style>
.routes-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  background-color: white;
}

.routes-table th,
.routes-table td {
  padding: 0.75rem;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.controlButtons {
  display: flex;
}

.controlButtons button {
  color: #ffffff;
  text-decoration: none;
  font-weight: bold;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background 0.3s;
  border-width: 0;
  cursor: pointer;
  margin: 0 0.5rem;
}

.controlButtons .editButton {
  color: black;
  background-color: rgb(241, 241, 0);
}

.editButton:hover {
  background-color: rgb(223, 223, 0);
}

.controlButtons .deleteButton {
  color: #ffffff;
  background-color: rgb(243, 0, 0);
}

.deleteButton:hover {
  background-color: rgb(221, 0, 0);
}

.routes-table th {
  background-color: #f3f4f6;
  font-weight: bold;
  color: #333;
}

.routes-table tr:hover {
  background-color: #f9f9f9;
}

.routes-table td {
  color: #555;
}

.no-results {
  text-align: center;
  color: #888;
  font-style: italic;
  margin-top: 1rem;
}
</style>
