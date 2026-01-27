package org.dismefront.ejb;

import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;

public interface NavigatorBusinessRemote {
    
    RouteListResponseDto findRoutesBetweenLocations(Long idFrom, Long idTo, String orderBy, int page, int size);
    
    RouteResponseDto addNewRoute(Long idFrom, Long idTo, Integer distance);
}