package org.dismefront.model;

import lombok.Data;
import java.time.Instant;

@Data
public class Route {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private Instant creationDate;
    private FromLocation from;
    private ToLocation to;
    private Integer distance;
}