import { navigatorClientApi } from './ navigatorService';
import { routerClientApi } from './routeService';

export default {
  ...routerClientApi,
  ...navigatorClientApi,
};
