package org.dismefront.service;

import org.dismefront.client.RouteServiceClient;
import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.model.Route;
import org.dismefront.util.RouteMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
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
        List<Route> allRoutes = routeServiceClient.getAllRoutes();

        List<Route> filteredRoutes = new ArrayList<>(allRoutes);

        sortRoutes(filteredRoutes, orderBy);
        
        int fromIndex = page * size;
        int toIndex = Math.min((page + 1) * size, filteredRoutes.size());

        if (fromIndex >= filteredRoutes.size()) {
            filteredRoutes = new ArrayList<>();
        } else {
            filteredRoutes = filteredRoutes.subList(fromIndex, toIndex);
        }
        
        List<RouteResponseDto> routeDtos = filteredRoutes.stream()
                .map(routeMapper::toResponseDto)
                .collect(Collectors.toList());

        RouteListResponseDto responseDto = new RouteListResponseDto();
        responseDto.setRoutes(routeDtos);

        responseDto.setCurrentPage(page);
        responseDto.setPageSize(size);
        responseDto.setTotalElements(allRoutes.size());
        responseDto.setTotalPages((int) Math.ceil((double) allRoutes.size() / size));
        
        return responseDto;
    }

    public RouteResponseDto addNewRoute(Long idFrom, Long idTo, Integer distance) {
        // Create a new route with the provided parameters
        Route route = new Route();
        route.setDistance(distance);
        // In a real implementation, we would set from and to locations based on idFrom and idTo
        
        // Use the Route service client to create the route and return the response
        Route createdRoute = routeServiceClient.createRoute(route);
        return routeMapper.toResponseDto(createdRoute);
    }
    
    private void sortRoutes(List<Route> routes, String orderBy) {
        Comparator<Route> comparator = null;
        
        switch (orderBy) {
            case "id":
                comparator = Comparator.comparing(Route::getId, Comparator.nullsLast(Long::compareTo));
                break;
            case "name":
                comparator = Comparator.comparing(Route::getName, Comparator.nullsLast(String::compareTo));
                break;
            case "creationDate":
                comparator = Comparator.comparing(Route::getCreationDate, Comparator.nullsLast(LocalDateTime::compareTo));
                break;
            case "distance":
                comparator = Comparator.comparing(Route::getDistance, Comparator.nullsLast(Integer::compareTo));
                break;
            case "coordinates.x":
                comparator = Comparator.comparing(
                    route -> route.getCoordinates() != null ? route.getCoordinates().getX() : null,
                    Comparator.nullsLast(Double::compareTo)
                );
                break;
            case "coordinates.y":
                comparator = Comparator.comparing(
                    route -> route.getCoordinates() != null ? route.getCoordinates().getY() : null,
                    Comparator.nullsLast(Float::compareTo)
                );
                break;
            case "from.x":
                comparator = Comparator.comparing(
                    route -> route.getFrom() != null ? route.getFrom().getX() : null,
                    Comparator.nullsLast(Integer::compareTo)
                );
                break;
            case "from.y":
                comparator = Comparator.comparing(
                    route -> route.getFrom() != null ? route.getFrom().getY() : null,
                    Comparator.nullsLast(Integer::compareTo)
                );
                break;
            case "from.z":
                comparator = Comparator.comparing(
                    route -> route.getFrom() != null ? route.getFrom().getZ() : null,
                    Comparator.nullsLast(Float::compareTo)
                );
                break;
            case "to.x":
                comparator = Comparator.comparing(
                    route -> route.getTo() != null ? route.getTo().getX() : null,
                    Comparator.nullsLast(Float::compareTo)
                );
                break;
            case "to.y":
                comparator = Comparator.comparing(
                    route -> route.getTo() != null ? route.getTo().getY() : null,
                    Comparator.nullsLast(Double::compareTo)
                );
                break;
            case "to.z":
                comparator = Comparator.comparing(
                    route -> route.getTo() != null ? route.getTo().getZ() : null,
                    Comparator.nullsLast(Float::compareTo)
                );
                break;
            default:
                comparator = Comparator.comparing(Route::getId, Comparator.nullsLast(Long::compareTo));
                break;
        }
        
        routes.sort(comparator);
    }
}