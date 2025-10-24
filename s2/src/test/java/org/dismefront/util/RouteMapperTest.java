package org.dismefront.util;

import org.dismefront.navigator.dto.RouteResponseDto;
import org.dismefront.navigator.model.Coordinates;
import org.dismefront.navigator.model.Route;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class RouteMapperTest {

    private final RouteMapper mapper = new RouteMapper();

    @Test
    public void testToResponseDto() {
        // Given
        Route route = new Route();
        route.setId(1L);
        route.setName("Test Route");
        route.setDistance(100);
        route.setCreationDate(LocalDateTime.now());
        
        Coordinates coordinates = new Coordinates();
        coordinates.setX(10.5);
        coordinates.setY(20.3f);
        route.setCoordinates(coordinates);

        // When
        RouteResponseDto dto = mapper.toResponseDto(route);

        // Then
        assertNotNull(dto);
        assertEquals(route.getId(), dto.getId());
        assertEquals(route.getName(), dto.getName());
        assertEquals(route.getDistance(), dto.getDistance());
        assertEquals(route.getCreationDate(), dto.getCreationDate());
        assertEquals(route.getCoordinates(), dto.getCoordinates());
    }

    @Test
    public void testToResponseDtoWithNullInput() {
        // When
        RouteResponseDto dto = mapper.toResponseDto(null);

        // Then
        assertNull(dto);
    }
}