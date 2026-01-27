package org.dismefront.soap;

import jakarta.jws.WebService;
import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.service.NavigatorService;
import org.dismefront.soap.dto.RouteListSoapDto;
import org.dismefront.soap.dto.RouteSoapDto;
import org.dismefront.soap.mapper.NavigatorSoapMapper;
import org.springframework.stereotype.Component;

@Component
@WebService(
        endpointInterface = "org.dismefront.soap.NavigatorSoapService",
        targetNamespace = "http://dismefront.org/navigator",
        serviceName = "NavigatorService"
)
public class NavigatorSoapServiceImpl implements NavigatorSoapService {

    private final NavigatorService navigatorService;
    private final NavigatorSoapMapper mapper;

    public NavigatorSoapServiceImpl(NavigatorService navigatorService, NavigatorSoapMapper mapper) {
        this.navigatorService = navigatorService;
        this.mapper = mapper;
    }

    @Override
    public RouteListSoapDto findRoutesBetweenLocations(Long idFrom, Long idTo, String orderBy, int page, int size) {
        RouteListResponseDto result = navigatorService.findRoutesBetweenLocations(idFrom, idTo, orderBy, page, size);
        return mapper.toSoap(result);
    }

    @Override
    public RouteSoapDto addNewRoute(Long idFrom, Long idTo, Integer distance) {
        RouteResponseDto result = navigatorService.addNewRoute(idFrom, idTo, distance);
        return mapper.toSoap(result);
    }
}

