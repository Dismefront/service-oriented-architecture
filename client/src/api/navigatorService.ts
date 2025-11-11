import { navigatorService as navigatorApiClient } from './apiClient';
import { Route, RouteListResponse } from './models';

export class NavigatorService {
  // Find routes between locations
  static async findRoutesBetweenLocations(
    idFrom: number,
    idTo: number,
    orderBy: string,
    page: number = 0,
    size: number = 10
  ): Promise<RouteListResponse> {
    const response = await navigatorApiClient.findRoutesBetweenLocations(idFrom, idTo, orderBy, page, size);
    
    // Transform the response to match our RouteListResponse interface
    return {
      routes: response.routes || [],
      totalPages: response.totalPages || 1,
      totalElements: response.totalElements || 0,
      currentPage: response.currentPage || page,
      pageSize: response.pageSize || size
    };
  }

  // Add a new route
  static async addNewRoute(idFrom: number, idTo: number, distance: number): Promise<Route> {
    const response = await navigatorApiClient.addNewRoute(idFrom, idTo, distance);
    return response;
  }
}