package org.dismefront.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Route")
public class RouteSoapDto {
    private Long id;
    private String name;
    private CoordinatesSoapDto coordinates;
    /**
     * ISO-8601 timestamp string (maps from/to java.time.Instant).
     */
    private String creationDate;
    private FromLocationSoapDto from;
    private ToLocationSoapDto to;
    private Integer distance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinatesSoapDto getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesSoapDto coordinates) {
        this.coordinates = coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public FromLocationSoapDto getFrom() {
        return from;
    }

    public void setFrom(FromLocationSoapDto from) {
        this.from = from;
    }

    public ToLocationSoapDto getTo() {
        return to;
    }

    public void setTo(ToLocationSoapDto to) {
        this.to = to;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
