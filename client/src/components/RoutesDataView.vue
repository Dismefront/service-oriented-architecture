<script setup lang="ts">
import type { Route } from '@/api/interfaces';
import { onMounted, ref } from 'vue';
import RouteInfoPopup from './RouteInfoPopup.vue';
import client from '@/api/client';
import { routeToFormI } from '@/api/utils';
import { useRoutesStore } from '@/stores/routes';

const routesStore = useRoutesStore();

onMounted(() => {
  routesStore.updateRoutes();
});

const popupEditOpened = ref<boolean>(false);
const editedId = ref<number | string>(-1);

const selectedRoute = ref<Required<Route> | null>(null);

const handleEditClick = (route: Required<Route>) => {
  editedId.value = route.id;
  selectedRoute.value = route;
  popupEditOpened.value = true;
};

const handleDeleteClick = (route: Required<Route>) => {
  if (confirm(`Удалить элемент ${route.id} '${route.name}'`)) {
    client.deleteRoute(route.id).then(() => {
      routesStore.updateRoutes();
    });
  }
};
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
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="route in routesStore.routes" :key="route.id">
        <td>{{ route.id }}</td>
        <td>{{ route.name }}</td>
        <td>{{ route.coordinates.x }}, {{ route.coordinates.y }}</td>
        <td>{{ route.from.x }}, {{ route.from.y }}, {{ route.from.z }}</td>
        <td>{{ route.to.x }}, {{ route.to.y }}, {{ route.to.z }}</td>
        <td>{{ route.distance.toFixed(2) }} km</td>
        <td>
          <div class="controlButtons">
            <button @click="handleEditClick(route)" class="editButton">edit</button>
            <button @click="handleDeleteClick(route)" class="deleteButton">delete</button>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <p v-if="routesStore.routes?.length === 0" class="no-results">No routes found.</p>
  <p v-if="routesStore.routes === null" class="no-results">Loading...</p>
  <RouteInfoPopup
    :title="`Edit Route ${editedId}`"
    :is-visible="popupEditOpened"
    :close-on-click-outside="true"
    :show-close-button="true"
    :route-init-data="selectedRoute ? routeToFormI(selectedRoute) : undefined"
    @close="popupEditOpened = false"
    @confirm="client.updateRoute"
  />
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
