package org.dismefront.soap.mapper;

import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.model.Coordinates;
import org.dismefront.model.FromLocation;
import org.dismefront.model.ToLocation;
import org.dismefront.soap.dto.CoordinatesSoapDto;
import org.dismefront.soap.dto.FromLocationSoapDto;
import org.dismefront.soap.dto.RouteListSoapDto;
import org.dismefront.soap.dto.RouteSoapDto;
import org.dismefront.soap.dto.ToLocationSoapDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class NavigatorSoapMapper {

    public RouteSoapDto toSoap(RouteResponseDto dto) {
        if (dto == null) return null;

        RouteSoapDto soap = new RouteSoapDto();
        soap.setId(dto.getId());
        soap.setName(dto.getName());
        soap.setDistance(dto.getDistance());
        soap.setCreationDate(dto.getCreationDate() != null ? dto.getCreationDate().toString() : null);
        soap.setCoordinates(toSoap(dto.getCoordinates()));
        soap.setFrom(toSoap(dto.getFrom()));
        soap.setTo(toSoap(dto.getTo()));
        return soap;
    }

    public RouteListSoapDto toSoap(RouteListResponseDto dto) {
        if (dto == null) return null;

        RouteListSoapDto soap = new RouteListSoapDto();
        soap.setTotalPages(dto.getTotalPages());
        soap.setTotalElements(dto.getTotalElements());
        soap.setCurrentPage(dto.getCurrentPage());
        soap.setPageSize(dto.getPageSize());
        if (dto.getRoutes() != null) {
            soap.setRoutes(dto.getRoutes().stream().map(this::toSoap).collect(Collectors.toList()));
        }
        return soap;
    }

    public RouteResponseDto fromSoap(RouteSoapDto soap) {
        if (soap == null) return null;

        RouteResponseDto dto = new RouteResponseDto();
        dto.setId(soap.getId());
        dto.setName(soap.getName());
        dto.setDistance(soap.getDistance());
        dto.setCreationDate(parseInstantOrNull(soap.getCreationDate()));
        dto.setCoordinates(fromSoap(soap.getCoordinates()));
        dto.setFrom(fromSoap(soap.getFrom()));
        dto.setTo(fromSoap(soap.getTo()));
        return dto;
    }

    public RouteListResponseDto fromSoap(RouteListSoapDto soap) {
        if (soap == null) return null;

        RouteListResponseDto dto = new RouteListResponseDto();
        dto.setTotalPages(soap.getTotalPages());
        dto.setTotalElements(soap.getTotalElements());
        dto.setCurrentPage(soap.getCurrentPage());
        dto.setPageSize(soap.getPageSize());
        List<RouteSoapDto> routes = soap.getRoutes();
        if (routes != null) {
            dto.setRoutes(routes.stream().filter(Objects::nonNull).map(this::fromSoap).collect(Collectors.toList()));
        }
        return dto;
    }

    private CoordinatesSoapDto toSoap(Coordinates c) {
        if (c == null) return null;
        CoordinatesSoapDto soap = new CoordinatesSoapDto();
        soap.setX(c.getX());
        soap.setY(c.getY());
        return soap;
    }

    private Coordinates fromSoap(CoordinatesSoapDto soap) {
        if (soap == null) return null;
        Coordinates c = new Coordinates();
        c.setX(soap.getX());
        c.setY(soap.getY());
        return c;
    }

    private FromLocationSoapDto toSoap(FromLocation from) {
        if (from == null) return null;
        FromLocationSoapDto soap = new FromLocationSoapDto();
        soap.setX(from.getX());
        soap.setY(from.getY());
        soap.setZ(from.getZ());
        return soap;
    }

    private FromLocation fromSoap(FromLocationSoapDto soap) {
        if (soap == null) return null;
        FromLocation from = new FromLocation();
        from.setX(soap.getX());
        from.setY(soap.getY());
        from.setZ(soap.getZ());
        return from;
    }

    private ToLocationSoapDto toSoap(ToLocation to) {
        if (to == null) return null;
        ToLocationSoapDto soap = new ToLocationSoapDto();
        soap.setX(to.getX());
        soap.setY(to.getY());
        soap.setZ(to.getZ());
        return soap;
    }

    private ToLocation fromSoap(ToLocationSoapDto soap) {
        if (soap == null) return null;
        ToLocation to = new ToLocation();
        to.setX(soap.getX());
        to.setY(soap.getY());
        to.setZ(soap.getZ());
        return to;
    }

    private Instant parseInstantOrNull(String isoInstant) {
        if (isoInstant == null || isoInstant.isBlank()) return null;
        return Instant.parse(isoInstant);
    }
}

