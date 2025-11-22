package org.dismefront.controller;

import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.service.NavigatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/navigator")
public class NavigatorController {

    private final NavigatorService navigatorService;

    public NavigatorController(NavigatorService navigatorService) {
        this.navigatorService = navigatorService;
    }

    @GetMapping("/routes/{id-from}/{id-to}/{order-by}")
    public ResponseEntity<RouteListResponseDto> findRoutesBetweenLocations(
            @PathVariable("id-from") Long idFrom,
            @PathVariable("id-to") Long idTo,
            @PathVariable("order-by") String orderBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        
        RouteListResponseDto routes = navigatorService.findRoutesBetweenLocations(
                idFrom, idTo, orderBy, page, size
        );
        return ResponseEntity.ok(routes);
    }

    @PostMapping("/route/add/{id-from}/{id-to}/{distance}")
    public ResponseEntity<RouteResponseDto> addNewRoute(
            @PathVariable("id-from") Long idFrom,
            @PathVariable("id-to") Long idTo,
            @PathVariable("distance") Integer distance
    ) {
        
        RouteResponseDto route = navigatorService.addNewRoute(idFrom, idTo, distance);
        return ResponseEntity.status(201).body(route);
    }
}