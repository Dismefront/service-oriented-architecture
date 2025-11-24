import client from '@/api/client';
import type { Route } from '@/api/interfaces';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useRoutesStore = defineStore('routes', () => {
  const routes = ref<Required<Route>[] | null>(null);
  const page = ref<number>(0);
  const size = ref<number>(10);
  const sort = ref<string>('id:asc');
  const queryString = ref<string>('');
  const filterString = ref<string>('');

  const handleRoutesUpdate = (data: Required<Route>[]) => {
    if (data === null) {
      routes.value = [];
      return;
    }
    routes.value = data;
  };

  function updateRoutes() {
    client
      .getRoutesList({
        page: page.value,
        size: size.value,
        sort: sort.value,
        filter: filterString.value,
      })
      .then(handleRoutesUpdate);
  }

  function searchBySubstring() {
    client.searchBySubstring(queryString.value).then(handleRoutesUpdate);
  }

  return { updateRoutes, page, size, sort, routes, searchBySubstring, queryString, filterString };
});
