import { routeService as routeApiClient } from './apiClient';
import { Route, RouteListResponse } from './models';

export interface RouteFilter {
  [key: string]: any;
}

export interface RouteSort {
  field: string;
  direction: 'asc' | 'desc';
}

export class RouteService {
  // Get all routes with pagination, sorting, and filtering
  static async getAllRoutes(
    page: number = 0,
    size: number = 10,
    sort?: RouteSort,
    filter?: RouteFilter
  ): Promise<RouteListResponse> {
    // Convert sort object to string format
    let sortParam: string | undefined;
    if (sort) {
      sortParam = `${sort.field}:${sort.direction}`;
    }

    // Convert filter object to string format
    let filterParam: string | undefined;
    if (filter) {
      filterParam = Object.entries(filter)
        .map(([key, value]) => `${key}=${value}`)
        .join(',');
    }

    const response = await routeApiClient.getAllRoutes(page, size, sortParam, filterParam);
    
    // Transform the response to match our RouteListResponse interface
    return {
      routes: response,
      totalPages: 1, // This would come from response headers in a real implementation
      totalElements: response.length, // This would come from response headers in a real implementation
      currentPage: page,
      pageSize: size
    };
  }

  // Get route by ID
  static async getRouteById(id: number): Promise<Route> {
    return await routeApiClient.getRouteById(id);
  }

  // Create a new route
  static async createRoute(route: Route): Promise<Route> {
    return await routeApiClient.createRoute(route);
  }

  // Update a route
  static async updateRoute(id: number, route: Route): Promise<Route> {
    return await routeApiClient.updateRoute(id, route);
  }

  // Delete a route
  static async deleteRoute(id: number): Promise<void> {
    return await routeApiClient.deleteRoute(id);
  }

  // Search routes by name substring
  static async searchRoutesByName(substring: string): Promise<Route[]> {
    return await routeApiClient.searchRoutesByName(substring);
  }

  // Get unique distances
  static async getUniqueDistances(): Promise<number[]> {
    return await routeApiClient.getUniqueDistances();
  }

  // Delete route by distance
  static async deleteRouteByDistance(distance: number): Promise<void> {
    return await routeApiClient.deleteRouteByDistance(distance);
  }
}