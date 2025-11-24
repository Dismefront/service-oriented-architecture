import type { Coordinates, FromLocation, Route, ToLocation } from '../interfaces';
import { fetchApi } from './fetchApi';

export const ROUTE_SERVICE_DEFAULT_URL = 'https://localhost:23151/lab2/api/v1';

export const routerClientApi = {
  getRoutesList(props: {
    page?: number;
    size?: number;
    sort?: string;
    filter?: string;
  }): Promise<Required<Route>[]> {
    const { page = 0, size = 10, sort = 'id:asc', filter = '' } = props;
    const params = new URLSearchParams();
    params.append('page', page.toString());
    params.append('size', size.toString());
    params.append('sort', sort);
    params.append('filter', filter);
    const url = `${ROUTE_SERVICE_DEFAULT_URL}/routes?${params.toString()}`;
    return fetchApi(url);
  },

  createRoute(props: {
    name: string;
    coordinates: Coordinates;
    fromLocation: FromLocation;
    toLocation: ToLocation;
    distance: number;
  }): Promise<Route> {
    const url = `${ROUTE_SERVICE_DEFAULT_URL}/routes`;
    return fetchApi(url, {
      method: 'POST',
      body: JSON.stringify(props),
    });
  },

  getRoute(id: number | string): Promise<Route> {
    const url = `${ROUTE_SERVICE_DEFAULT_URL}/routes/${id}`;
    return fetchApi(url);
  },

  updateRoute(props: Required<Route>): Promise<Route> {
    const { id, ...obj } = props;
    const url = `${ROUTE_SERVICE_DEFAULT_URL}/routes/${id}`;
    return fetchApi(url, {
      method: 'PUT',
      body: JSON.stringify(obj),
    });
  },

  deleteRoute(id: number | string) {
    const url = `${ROUTE_SERVICE_DEFAULT_URL}/routes/${id}`;
    return fetchApi(url, {
      method: 'DELETE',
    });
  },

  deleteRoutesByDistance(distance: number) {
    const url = `${ROUTE_SERVICE_DEFAULT_URL}/routes/by-distance/${distance}`;
    return fetchApi(url, {
      method: 'DELETE',
    });
  },

  searchBySubstring(substring: string): Promise<Required<Route>[]> {
    const url = `${ROUTE_SERVICE_DEFAULT_URL}/routes/search/${substring}`;
    return fetchApi(url);
  },

  getUniqueDistances(): Promise<number[]> {
    const url = `${ROUTE_SERVICE_DEFAULT_URL}/routes/unique-distances`;
    return fetchApi(url);
  },
};
