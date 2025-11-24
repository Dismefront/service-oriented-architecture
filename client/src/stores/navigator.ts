import client from '@/api/client';
import type { Route } from '@/api/interfaces';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useNavigatorStore = defineStore('navigator', () => {
  const routes = ref<Required<Route>[] | null>(null);
  const idFromFind = ref<number | string>(0);
  const idToFind = ref<number | string>(0);
  const orderBy = ref<string>('');
  const page = ref<number>(0);

  const idFromAdd = ref<number | string>(0);
  const idToAdd = ref<number | string>(0);
  const distanceAdd = ref<number>(0);

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const handleRoutesUpdate = (data: any) => {
    routes.value = data.routes;
  };

  function updateRoutes() {
    client
      .getRoutesBetweenLocations({
        idFrom: idFromFind.value,
        idTo: idToFind.value,
        orderBy: orderBy.value,
        page: page.value,
      })
      .then(handleRoutesUpdate)
      .catch(() => {
        routes.value = [];
      });
  }

  return {
    updateRoutes,
    routes,
    orderBy,
    idFromFind,
    idToFind,
    idFromAdd,
    idToAdd,
    distanceAdd,
    page,
  };
});
