<script setup lang="ts">
import { onMounted, reactive, ref, type SelectHTMLAttributes } from 'vue';
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

enum FilterTypes {
  Eq,
  Get,
  Fet,
  Range,
}

interface FilterBase {
  field: keyof typeof sortingMapper;
  type: FilterTypes;
  val1?: string;
  val2?: string;
  key: string;
}

const filtersArray = reactive<FilterBase[]>([]);

const handleAddFilter = () => {
  filtersArray.push({
    field: 'id',
    type: FilterTypes.Eq,
    val1: '',
    val2: '',
    key: crypto.randomUUID(),
  });
};

const handleRemoveFilter = (key: string) => {
  filtersArray.splice(
    filtersArray.findIndex((v) => v.key === key),
    1,
  );
};

const handleSortParamsChange = () => {
  const sortByVal = sortBySelectRef.value?.value as keyof typeof sortingMapper;
  const sortOrderVal = sortOrderSelectRef.value?.value;
  if (sortByVal && sortOrderVal) {
    routesStore.sort = `${sortingMapper[sortByVal]}:${sortOrderVal}`;
  }
  console.log(routesStore.sort);
};

const constructFilterSearchString = () => {
  const filterParts: string[] = [];

  filtersArray.forEach((filter) => {
    if (!filter.val1 || (filter.type == FilterTypes.Range && !filter.val2)) return;

    const fieldName = sortingMapper[filter.field];

    switch (filter.type) {
      case FilterTypes.Eq:
        if (filter.val1) {
          filterParts.push(`${fieldName}=${filter.val1}`);
        }
        break;

      case FilterTypes.Fet:
        console.log('<=');

        if (filter.val1) {
          filterParts.push(`${fieldName}=~${filter.val1}`);
        }
        break;

      case FilterTypes.Get:
        if (filter.val1) {
          filterParts.push(`${fieldName}=${filter.val1}~`);
        }
        console.log('>=');
        break;

      case FilterTypes.Range:
        if (filter.val1 && filter.val2) {
          filterParts.push(`${fieldName}=${filter.val1}~${filter.val2}`);
        } else if (filter.val1) {
          filterParts.push(`${fieldName}=${filter.val1}~`);
        } else if (filter.val2) {
          filterParts.push(`${fieldName}=~${filter.val2}`);
        }
        break;
    }
  });

  routesStore.filterString = filterParts.join(',');
};

const handleSearchButton = () => {
  constructFilterSearchString();
  routesStore.updateRoutes();
};

onMounted(() => {
  handleSortParamsChange();
});
</script>

<template>
  <div class="inputs-container">
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

    <div class="filters">
      <div class="sorting gp" v-for="filter in filtersArray" v-bind:key="filter.key">
        <select
          name=""
          :value="filter.type"
          @change="
            (e: Event) => {
              filter.type = Number.parseInt((e.target as HTMLSelectElement).value);
            }
          "
        >
          <option :value="FilterTypes.Eq">=</option>
          // eslint-disable-next-line vue/no-parsing-error
          <option :value="FilterTypes.Fet">{{ '<=' }}</option>
          <option :value="FilterTypes.Get">>=</option>
          <option :value="FilterTypes.Range">Range</option>
        </select>
        <select
          name=""
          :value="filter.field"
          @change="
            (e: Event) => {
              filter.field = (e.target as SelectHTMLAttributes).value;
            }
          "
        >
          <option value="id">ID</option>
          <option value="name">Name</option>
          <option value="creationDate">Creation Date</option>
          <option value="distance">Distance</option>
          <option value="coordinatesX">X Coordinate</option>
          <option value="coordinatesY">Y Coordinate</option>
          <option value="fromX">X From-Location</option>
          <option value="fromY">Y From-Location</option>
          <option value="fromZ">Z From-Location</option>
          <option value="toX">X To-Location</option>
          <option value="toY">Y To-Location</option>
          <option value="toZ">Z To-Location</option>
        </select>
        <input
          v-if="filter.type != FilterTypes.Range"
          class="filterInput"
          placeholder="value"
          v-model="filter.val1"
        />
        <input
          v-if="filter.type == FilterTypes.Range"
          class="filterInput"
          placeholder="from"
          v-model="filter.val1"
        />
        <input
          v-if="filter.type == FilterTypes.Range"
          class="filterInput"
          placeholder="to"
          v-model="filter.val2"
        />
        <button class="removeBtn" @click="handleRemoveFilter(filter.key)">Remove</button>
      </div>
      <button class="createBtn" style="background-color: blue" @click="handleAddFilter">
        Add filter
      </button>
    </div>

    <div class="action-buttons">
      <button @click="handleSearchButton" class="applyBtn">Search</button>
      <button @click="createPopupVisible = true" class="createBtn">Create</button>
    </div>

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
.inputs-container {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1.5rem;
  align-items: left;
}

.gp {
  margin: 5px 0;
}

.searchbar {
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
}

.searchbar input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 200px;
}

.filterInput {
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

.action-buttons {
  display: flex;
  gap: 0.5rem;
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

.removeBtn {
  padding: 0.5rem 1rem;
  background: rgb(153, 18, 0);
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
