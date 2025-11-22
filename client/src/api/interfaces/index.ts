export interface Coordinates {
  x: number;
  y: number;
}

export interface FromLocation {
  x: number;
  y: number;
  z: number;
}

export interface ToLocation {
  x: number;
  y: number;
  z: number;
}

export interface Route {
  readonly id?: number;
  name: string;
  coordinates: Coordinates;
  from: FromLocation;
  to: ToLocation;
  distance: number;
}

export interface RouteFormI {
  id?: number;
  name: string;
  coordinatesX: number;
  coordinatesY: number;
  fromX: number;
  fromY: number;
  fromZ: number;
  toX: number;
  toY: number;
  toZ: number;
  distance: number;
}
