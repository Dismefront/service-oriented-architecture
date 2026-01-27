package org.dismefront.ejb;

import org.dismefront.client.RouteServiceClient;
import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.model.Route;
import org.dismefront.util.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NavigatorBusinessBean implements NavigatorBusinessRemote {
    
    @Autowired
    private RouteServiceClient routeServiceClient;
    
    @Autowired
    private RouteMapper routeMapper;

    @Override
    public RouteListResponseDto findRoutesBetweenLocations(Long idFrom, Long idTo, String orderBy, int page, int size) {
        String sortParam = convertOrderByToSort(orderBy);
        String filter = "from.id=" + idFrom.toString() + ",to.id=" + idTo.toString();
        
        List<Route> routes = routeServiceClient.getAllRoutes(page, size, sortParam, filter);
        
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
    
    @Override
    public RouteResponseDto addNewRoute(Long idFrom, Long idTo, Integer distance) {
        Route createdRoute = routeServiceClient.createRoute(idFrom, idTo, distance);
        return routeMapper.toResponseDto(createdRoute);
    }
    
    private String convertOrderByToSort(String orderBy) {
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