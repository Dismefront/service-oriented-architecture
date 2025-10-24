package org.dismefront.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Route {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private FromLocation from;
    private ToLocation to;
    private Integer distance;
}