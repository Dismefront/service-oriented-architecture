// Data models for Route service

export interface Coordinates {
  x: number;
  y: number;
}

export interface FromLocation {
  x?: number;
  y?: number;
  z?: number;
}

export interface ToLocation {
  x: number;
  y?: number;
  z?: number;
}

export interface Route {
  id?: number;
  name: string;
  coordinates: Coordinates;
  creationDate?: string;
  from?: FromLocation;
  to: ToLocation;
  distance: number;
}

// Response models
export interface RouteListResponse {
  routes: Route[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
  pageSize: number;
}

export interface RouteResponse {
  id?: number;
  name: string;
  coordinates: Coordinates;
  creationDate?: string;
  from?: FromLocation;
  to: ToLocation;
  distance: number;
}