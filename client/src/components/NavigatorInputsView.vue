<script setup lang="ts">
import client from '@/api/client';
import { useNavigatorStore } from '@/stores/navigator';
import { onMounted, ref } from 'vue';

const sortBySelectRef = ref<HTMLSelectElement>();

const navigatorsStore = useNavigatorStore();

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
  if (sortByVal) {
    navigatorsStore.orderBy = sortingMapper[sortByVal];
  }
};

const handleAdd = () => {
  client
    .addRouteBetweenLocations({
      idFrom: navigatorsStore.idFromAdd,
      idTo: navigatorsStore.idToAdd,
      distance: navigatorsStore.distanceAdd,
    })
    .then(() => {
      navigatorsStore.updateRoutes();
    });
};

onMounted(() => {
  handleSortParamsChange();
});
</script>

<template>
  <div class="filters">
    <div class="searchbar">
      <label>page</label>
      <input v-model.number="navigatorsStore.page" type="number" placeholder="Page" />
    </div>
    <div class="searchbar">
      <label>id-from</label>
      <input
        v-model.number="navigatorsStore.idFromFind"
        type="number"
        placeholder="Route id from..."
      />
    </div>
    <div class="searchbar">
      <label>id-to</label>
      <input v-model.number="navigatorsStore.idToFind" type="number" placeholder="Route id to..." />
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

    <button @click="navigatorsStore.updateRoutes" class="applyBtn">Search</button>
  </div>
  <div class="filters">
    <div class="searchbar">
      <label>id-from</label>
      <input v-model.number="navigatorsStore.idFromAdd" type="number" placeholder="Route id..." />
    </div>
    <div class="searchbar">
      <label>id-to</label>
      <input v-model.number="navigatorsStore.idToAdd" type="number" placeholder="Route id..." />
    </div>
    <div class="searchbar">
      <label>distance</label>
      <input v-model.number="navigatorsStore.distanceAdd" type="number" placeholder="Route id..." />
    </div>

    <button @click="handleAdd" class="createBtn">Add</button>
  </div>
</template>

<style scoped>
.filters {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 1.5rem;
  align-items: end;
}

.searchbar {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.searchbar input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100px;
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
