import type { Route } from '../interfaces';
import { fetchApi } from './fetchApi';

export const NAVIGATOR_SERVICE_DEFAULT_URL = 'http://localhost:23169/navigator';

export const navigatorClientApi = {
  getRoutesBetweenLocations(props: {
    idFrom: number | string;
    idTo: number | string;
    orderBy?: string;
    page?: number;
  }): Promise<Required<Route>[]> {
    const { idFrom, idTo, orderBy = 'id', page = 0 } = props;
    const params = new URLSearchParams();
    params.append('page', page.toString());
    const url = `${NAVIGATOR_SERVICE_DEFAULT_URL}/routes/${idFrom}/${idTo}/${orderBy}?${params.toString()}`;
    return fetchApi(url);
  },

  addRouteBetweenLocations(props: {
    idFrom: number | string;
    idTo: number | string;
    distance: number;
  }): Promise<Route> {
    const { idFrom, idTo, distance } = props;
    const url = `${NAVIGATOR_SERVICE_DEFAULT_URL}/route/add/${idFrom}/${idTo}/${distance}`;
    return fetchApi(url, {
      method: 'POST',
    });
  },
};
