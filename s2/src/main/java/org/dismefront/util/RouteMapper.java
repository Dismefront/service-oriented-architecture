package org.dismefront.util;

import org.dismefront.dto.RouteResponseDto;
import org.dismefront.model.Route;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

    public RouteResponseDto toResponseDto(Route route) {
        if (route == null) {
            return null;
        }
        
        RouteResponseDto dto = new RouteResponseDto();
        dto.setId(route.getId());
        dto.setName(route.getName());
        dto.setCoordinates(route.getCoordinates());
        dto.setCreationDate(route.getCreationDate());
        dto.setFrom(route.getFrom());
        dto.setTo(route.getTo());
        dto.setDistance(route.getDistance());
        
        return dto;
    }
}