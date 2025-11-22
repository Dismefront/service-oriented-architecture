import client from '@/api/client';
import type { Route } from '@/api/interfaces';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useNavigatorStore = defineStore('navigator', () => {
  const routes = ref<Required<Route>[] | null>(null);
  const idFind = ref<number | string>(0);
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
        idFrom: idFind.value,
        idTo: idFind.value,
        orderBy: orderBy.value,
        page: page.value,
      })
      .then(handleRoutesUpdate);
  }

  return { updateRoutes, routes, orderBy, idFind, idFromAdd, idToAdd, distanceAdd, page };
});
