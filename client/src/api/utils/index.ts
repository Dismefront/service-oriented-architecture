import type { Route, RouteFormI } from '../interfaces';

export function routeToFormI(route: Required<Route>): Required<RouteFormI>;
export function routeToFormI(route: Route): RouteFormI;
export function routeToFormI(route: Route): RouteFormI {
  return {
    id: route.id,
    name: route.name,
    coordinatesX: route.coordinates.x,
    coordinatesY: route.coordinates.y,
    fromX: route.from.x,
    fromY: route.from.y,
    fromZ: route.from.z,
    toX: route.from.x,
    toY: route.from.y,
    toZ: route.from.z,
    distance: route.distance,
  };
}

export function formItoRoute(route: Required<RouteFormI>): Required<Route>;
export function formItoRoute(route: RouteFormI): Route;
export function formItoRoute(route: RouteFormI): Route {
  return {
    id: route.id,
    name: route.name,
    coordinates: {
      x: route.coordinatesX,
      y: route.coordinatesY,
    },
    from: {
      x: route.fromX,
      y: route.fromY,
      z: route.fromZ,
    },
    to: {
      x: route.toX,
      y: route.toY,
      z: route.toZ,
    },
    distance: route.distance,
  };
}
