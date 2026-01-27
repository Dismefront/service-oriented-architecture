package org.dismefront.controller;

import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.soap.NavigatorSoapService;
import org.dismefront.soap.dto.RouteListSoapDto;
import org.dismefront.soap.dto.RouteSoapDto;
import org.dismefront.soap.mapper.NavigatorSoapMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/navigator")
@CrossOrigin(origins = "*")
public class NavigatorController {

    private final NavigatorSoapService navigatorSoapService;
    private final NavigatorSoapMapper mapper;

    public NavigatorController(NavigatorSoapService navigatorSoapService, NavigatorSoapMapper mapper) {
        this.navigatorSoapService = navigatorSoapService;
        this.mapper = mapper;
    }

    @GetMapping("/routes/{id-from}/{id-to}/{order-by}")
    public ResponseEntity<RouteListResponseDto> findRoutesBetweenLocations(
            @PathVariable("id-from") Long idFrom,
            @PathVariable("id-to") Long idTo,
            @PathVariable("order-by") String orderBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        RouteListSoapDto routesSoap = navigatorSoapService.findRoutesBetweenLocations(
                idFrom, idTo, orderBy, page, size
        );
        RouteListResponseDto routes = mapper.fromSoap(routesSoap);
        return ResponseEntity.ok(routes);
    }

    @PostMapping("/route/add/{id-from}/{id-to}/{distance}")
    public ResponseEntity<RouteResponseDto> addNewRoute(
            @PathVariable("id-from") Long idFrom,
            @PathVariable("id-to") Long idTo,
            @PathVariable("distance") Integer distance
    ) {
        RouteSoapDto routeSoap = navigatorSoapService.addNewRoute(idFrom, idTo, distance);
        RouteResponseDto route = mapper.fromSoap(routeSoap);
        return ResponseEntity.status(201).body(route);
    }
}