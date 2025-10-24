package org.dismefront.dto;

import org.dismefront.navigator.model.Coordinates;
import org.dismefront.navigator.model.FromLocation;
import org.dismefront.navigator.model.ToLocation;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class RouteDtoTest {

    @Test
    public void testRouteResponseDtoToHumanReadableFormat() {
        // Given
        RouteResponseDto dto = new RouteResponseDto();
        dto.setId(1L);
        dto.setName("Test Route");
        dto.setDistance(100);
        dto.setCreationDate(LocalDateTime.of(2023, 1, 1, 12, 0));

        Coordinates coordinates = new Coordinates();
        coordinates.setX(10.5);
        coordinates.setY(20.3f);
        dto.setCoordinates(coordinates);

        FromLocation from = new FromLocation();
        from.setX(1);
        from.setY(2);
        from.setZ(3.5f);
        dto.setFrom(from);

        ToLocation to = new ToLocation();
        to.setX(100.5f);
        to.setY(200.8);
        to.setZ(300.9f);
        dto.setTo(to);

        // When
        String humanReadable = dto.toHumanReadableFormat();

        // Then
        assertTrue(humanReadable.contains("Route Details:"));
        assertTrue(humanReadable.contains("ID: 1"));
        assertTrue(humanReadable.contains("Name: Test Route"));
        assertTrue(humanReadable.contains("Distance: 100"));
        assertTrue(humanReadable.contains("X=10.5, Y=20.3"));
        assertTrue(humanReadable.contains("From Location: X=1, Y=2, Z=3.5"));
        assertTrue(humanReadable.contains("To Location: X=100.5, Y=200.8, Z=300.9"));
    }

    @Test
    public void testRouteListResponseDtoToTableFormat() {
        // Given
        RouteResponseDto route1 = new RouteResponseDto();
        route1.setId(1L);
        route1.setName("Short Name");
        route1.setDistance(50);
        route1.setCreationDate(LocalDateTime.of(2023, 1, 1, 12, 0));

        RouteResponseDto route2 = new RouteResponseDto();
        route2.setId(2L);
        route2.setName("This is a very long route name that should be truncated");
        route2.setDistance(100);
        route2.setCreationDate(LocalDateTime.of(2023, 1, 2, 12, 0));

        RouteListResponseDto listDto = new RouteListResponseDto();
        listDto.setRoutes(Arrays.asList(route1, route2));
        listDto.setTotalPages(1);
        listDto.setTotalElements(2);
        listDto.setCurrentPage(0);
        listDto.setPageSize(10);

        // When
        String tableFormat = listDto.toTableFormat();

        // Then
        assertTrue(tableFormat.contains("ID"));
        assertTrue(tableFormat.contains("Name"));
        assertTrue(tableFormat.contains("Distance"));
        assertTrue(tableFormat.contains("Creation Date"));
        assertTrue(tableFormat.contains("Short Name"));
        assertTrue(tableFormat.contains("This is a very long..."));
        assertTrue(tableFormat.contains("Page 1 of 1"));
        assertTrue(tableFormat.contains("Total: 2 routes"));
    }
}