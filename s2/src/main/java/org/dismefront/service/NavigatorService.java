package org.dismefront.service;

import org.dismefront.client.RouteServiceClient;
import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.model.Route;
import org.dismefront.util.RouteMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NavigatorService {

    private final RouteServiceClient routeServiceClient;
    private final RouteMapper routeMapper;

    public NavigatorService(RouteServiceClient routeServiceClient, RouteMapper routeMapper) {
        this.routeServiceClient = routeServiceClient;
        this.routeMapper = routeMapper;
    }

    public RouteListResponseDto findRoutesBetweenLocations(Long idFrom, Long idTo, String orderBy, 
                                                           int page, int size) {
        // In a real implementation, we would filter routes based on idFrom and idTo
        // For now, we'll get all routes and simulate the filtering
        // We'll construct a filter based on from.id and to.id if the Route model had these fields
        // Since our model doesn't have these fields, we'll just get all routes for demonstration
        
        // Convert orderBy parameter to sort parameter format expected by RouteService
        String sortParam = convertOrderByToSort(orderBy);
        
        List<Route> routes = routeServiceClient.getAllRoutes(page, size, sortParam, null);
        
        List<RouteResponseDto> routeDtos = routes.stream()
                .map(routeMapper::toResponseDto)
                .collect(Collectors.toList());

        RouteListResponseDto responseDto = new RouteListResponseDto();
        responseDto.setRoutes(routeDtos);
        // Set pagination info (simplified)
        responseDto.setCurrentPage(page);
        responseDto.setPageSize(size);
        responseDto.setTotalElements(routeDtos.size());
        responseDto.setTotalPages(1);
        
        return responseDto;
    }

    public RouteResponseDto addNewRoute(Long idFrom, Long idTo, Integer distance) {
        // Create a new route with the provided parameters
        Route route = new Route();
        route.setDistance(distance);
        // In a real implementation, we would set from and to locations based on idFrom and idTo
        
        Route createdRoute = routeServiceClient.createRoute(route);
        return routeMapper.toResponseDto(createdRoute);
    }
    
    private String convertOrderByToSort(String orderBy) {
        // Convert NavigatorService orderBy values to RouteService sort format
        switch (orderBy) {
            case "id":
                return "id:asc";
            case "name":
                return "name:asc";
            case "creationDate":
                return "creationDate:asc";
            case "distance":
                return "distance:asc";
            case "coordinates.x":
                return "coordinates.x:asc";
            case "coordinates.y":
                return "coordinates.y:asc";
            case "from.x":
                return "from.x:asc";
            case "from.y":
                return "from.y:asc";
            case "from.z":
                return "from.z:asc";
            case "to.x":
                return "to.x:asc";
            case "to.y":
                return "to.y:asc";
            case "to.z":
                return "to.z:asc";
            default:
                return null;
        }
    }
}