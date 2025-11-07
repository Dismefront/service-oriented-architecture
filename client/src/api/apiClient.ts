// Simple fetch-based API client

// Base URLs for services
const ROUTE_SERVICE_BASE_URL = 'http://localhost:8080/api/v1';
const NAVIGATOR_SERVICE_BASE_URL = 'http://localhost:8081/navigator';

// Generic fetch function with error handling
function fetchApi(url: string, options: RequestInit = {}) {
  return fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })
  .then(response => {
    if (!response.ok) {
      return response.text().then(text => {
        throw new Error(text || `HTTP error! status: ${response.status}`);
      });
    }
    
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.indexOf('application/json') !== -1) {
      return response.json();
    } else {
      return response.text();
    }
  })
  .catch(error => {
    throw new Error(error.message || 'An unknown error occurred');
  });
}

// Route Service API functions
export const routeService = {
  // Get all routes with pagination, sorting, and filtering
  getAllRoutes: (page: number = 0, size: number = 10, sort?: string, filter?: string) => {
    const params = new URLSearchParams();
    params.append('page', page.toString());
    params.append('size', size.toString());
    
    if (sort) params.append('sort', sort);
    if (filter) params.append('filter', filter);
    
    const url = `${ROUTE_SERVICE_BASE_URL}/routes?${params.toString()}`;
    return fetchApi(url);
  },

  // Get route by ID
  getRouteById: (id: number) => {
    const url = `${ROUTE_SERVICE_BASE_URL}/routes/${id}`;
    return fetchApi(url);
  },

  // Create a new route
  createRoute: (route: any) => {
    const url = `${ROUTE_SERVICE_BASE_URL}/routes`;
    return fetchApi(url, {
      method: 'POST',
      body: JSON.stringify(route),
    });
  },

  // Update a route
  updateRoute: (id: number, route: any) => {
    const url = `${ROUTE_SERVICE_BASE_URL}/routes/${id}`;
    return fetchApi(url, {
      method: 'PUT',
      body: JSON.stringify(route),
    });
  },

  // Delete a route
  deleteRoute: (id: number) => {
    const url = `${ROUTE_SERVICE_BASE_URL}/routes/${id}`;
    return fetchApi(url, {
      method: 'DELETE',
    });
  },

  // Search routes by name substring
  searchRoutesByName: (substring: string) => {
    const url = `${ROUTE_SERVICE_BASE_URL}/routes/search/${substring}`;
    return fetchApi(url);
  },

  // Get unique distances
  getUniqueDistances: () => {
    const url = `${ROUTE_SERVICE_BASE_URL}/routes/unique-distances`;
    return fetchApi(url);
  },

  // Delete route by distance
  deleteRouteByDistance: (distance: number) => {
    const url = `${ROUTE_SERVICE_BASE_URL}/routes/by-distance/${distance}`;
    return fetchApi(url, {
      method: 'DELETE',
    });
  },
};

// Navigator Service API functions
export const navigatorService = {
  // Find routes between locations
  findRoutesBetweenLocations: (idFrom: number, idTo: number, orderBy: string, page: number = 0, size: number = 10) => {
    const url = `${NAVIGATOR_SERVICE_BASE_URL}/routes/${idFrom}/${idTo}/${orderBy}?page=${page}&size=${size}`;
    return fetchApi(url);
  },

  // Add a new route
  addNewRoute: (idFrom: number, idTo: number, distance: number) => {
    const url = `${NAVIGATOR_SERVICE_BASE_URL}/route/add/${idFrom}/${idTo}/${distance}`;
    return fetchApi(url, {
      method: 'POST',
    });
  },
};

export default { routeService, navigatorService };