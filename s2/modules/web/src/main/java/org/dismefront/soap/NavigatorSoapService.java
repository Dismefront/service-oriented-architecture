package org.dismefront.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.dismefront.soap.dto.RouteListSoapDto;
import org.dismefront.soap.dto.RouteSoapDto;

@WebService(
        name = "NavigatorSoapService",
        targetNamespace = "http://dismefront.org/navigator"
)
public interface NavigatorSoapService {

    @WebMethod(operationName = "FindRoutesBetweenLocations")
    RouteListSoapDto findRoutesBetweenLocations(
            @WebParam(name = "idFrom") Long idFrom,
            @WebParam(name = "idTo") Long idTo,
            @WebParam(name = "orderBy") String orderBy,
            @WebParam(name = "page") int page,
            @WebParam(name = "size") int size
    );

    @WebMethod(operationName = "AddNewRoute")
    RouteSoapDto addNewRoute(
            @WebParam(name = "idFrom") Long idFrom,
            @WebParam(name = "idTo") Long idTo,
            @WebParam(name = "distance") Integer distance
    );
}

