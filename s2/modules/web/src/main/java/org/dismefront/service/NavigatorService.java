package org.dismefront.service;

import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.ejb.NavigatorBusinessRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavigatorService {
    
    @Autowired
    private NavigatorBusinessRemote navigatorBusiness;
    
    public RouteListResponseDto findRoutesBetweenLocations(Long idFrom, Long idTo, String orderBy,
                                                           int page, int size) {
        return navigatorBusiness.findRoutesBetweenLocations(idFrom, idTo, orderBy, page, size);
    }
    
    public RouteResponseDto addNewRoute(Long idFrom, Long idTo, Integer distance) {
        return navigatorBusiness.addNewRoute(idFrom, idTo, distance);
    }
}