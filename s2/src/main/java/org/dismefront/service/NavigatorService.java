package org.dismefront.service;

import org.dismefront.client.RouteServiceClient;
import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.model.Route;
import org.dismefront.util.RouteMapper;
import org.springframework.stereotype.Service;
import java.time.Instant;
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

    public RouteListResponseDto findRoutesBetweenLocations(Long idFrom, Long idTo, String orderBy,
                                                           int page, int size) {
        String sortParam = convertOrderByToSort(orderBy);

        List<Route> routes = routeServiceClient.getAllRoutes(page, size, sortParam, null);

        List<RouteResponseDto> routeDtos = routes.stream()
                .map(routeMapper::toResponseDto)
                .collect(Collectors.toList());

        RouteListResponseDto responseDto = new RouteListResponseDto();
        responseDto.setRoutes(routeDtos);

        responseDto.setCurrentPage(page);
        responseDto.setPageSize(size);
        responseDto.setTotalElements(routeDtos.size());
        responseDto.setTotalPages(1);

        return responseDto;
    }

    public RouteResponseDto addNewRoute(Long idFrom, Long idTo, Integer distance) {
        Route route = new Route();
        route.setDistance(distance);

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
                comparator = Comparator.comparing(Route::getCreationDate, Comparator.nullsLast(Instant::compareTo));
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
                // Default sorting by ID if orderBy is not recognized
                comparator = Comparator.comparing(Route::getId, Comparator.nullsLast(Long::compareTo));
                break;
        }
        
        routes.sort(comparator);
    }
}