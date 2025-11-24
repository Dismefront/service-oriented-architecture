<script setup lang="ts">
import { onMounted, ref } from 'vue';
import RouteInfoPopup from './RouteInfoPopup.vue';
import client from '@/api/client';
import { useRoutesStore } from '@/stores/routes';

const createPopupVisible = ref<boolean>(false);
const routesStore = useRoutesStore();

const sortBySelectRef = ref<HTMLSelectElement>();
const sortOrderSelectRef = ref<HTMLSelectElement>();

const sortingMapper = {
  id: 'id',
  name: 'name',
  creatinonDate: 'creationDate',
  distance: 'distance',
  coordinatesX: 'coordinates.x',
  coordinatesY: 'coordinates.y',
  fromX: 'from.x',
  fromY: 'from.y',
  fromZ: 'from.z',
  toY: 'to.y',
  toX: 'to.x',
  toZ: 'to.z',
};

const handleSortParamsChange = () => {
  const sortByVal = sortBySelectRef.value?.value as keyof typeof sortingMapper;
  const sortOrderVal = sortOrderSelectRef.value?.value;
  if (sortByVal && sortOrderVal) {
    routesStore.sort = `${sortingMapper[sortByVal]}:${sortOrderVal}`;
  }
  console.log(routesStore.sort);
};

onMounted(() => {
  handleSortParamsChange();
});
</script>

<template>
  <div class="filters">
    <div class="searchbar">
      <input v-model="routesStore.queryString" type="text" placeholder="Search routes..." />
      <button
        :disabled="!routesStore.queryString"
        @click="routesStore.searchBySubstring"
        class="substring-search-btn"
      >
        Substring search
      </button>
    </div>

    <div class="sorting">
      <select
        ref="sortBySelectRef"
        @change="handleSortParamsChange"
        name="routeSortBySelect"
        id="routeSortBySelect"
      >
        <option value="id">Sort by ID</option>
        <option value="name">Sort by Name</option>
        <option value="creationDate">Sort by Creation Date</option>
        <option value="distance">Sort by Distance</option>
        <option value="coordinatesX">Sort by X Coordinate</option>
        <option value="coordinatesY">Sort by Y Coordinate</option>
        <option value="fromX">Sort by X From-Location</option>
        <option value="fromY">Sort by Y From-Location</option>
        <option value="fromZ">Sort by Z From-Location</option>
        <option value="toX">Sort by X To-Location</option>
        <option value="toY">Sort by Y To-Location</option>
        <option value="toZ">Sort by Z To-Location</option>
      </select>
    </div>

    <div class="sorting">
      <select
        ref="sortOrderSelectRef"
        @change="handleSortParamsChange"
        name="routeSortOrderSelect"
        id="routeSortOrderSelect"
      >
        <option value="asc">Ascending</option>
        <option value="desc">Descending</option>
      </select>
    </div>

    <div class="searchbar">
      <input v-model="routesStore.filterString" type="text" placeholder="Filter routes..." />
    </div>

    <button @click="routesStore.updateRoutes" class="applyBtn">Search</button>
    <button @click="createPopupVisible = true" class="createBtn">Create</button>

    <RouteInfoPopup
      title="Create a route"
      :is-visible="createPopupVisible"
      :close-on-click-outside="true"
      :show-close-button="true"
      :onConfirm="client.createRoute"
      @close="createPopupVisible = false"
    />
  </div>
</template>

<style scoped>
.filters {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 1.5rem;
  align-items: center;
}

.searchbar {
  display: flex;
  gap: 0.5rem;
}

.searchbar input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 200px;
}

.searchbar button {
  padding: 0.5rem 1rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.sorting {
  display: flex;
  gap: 0.5rem;
  align-items: center;
}

.sorting select {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.applyBtn {
  padding: 0.5rem 1rem;
  background: #2c82e0;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}

.substring-search-btn {
  transition: background 0.3s;
}

.substring-search-btn:hover {
  background-color: #2dad74;
}

.applyBtn:hover {
  background: #2572c0;
}

.createBtn {
  padding: 0.5rem 1rem;
  background: rgb(0, 153, 0);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}

.createBtn:hover {
  background: green;
}
</style>
