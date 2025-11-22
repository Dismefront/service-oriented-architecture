package org.dismefront.dto;

import lombok.Data;
import org.dismefront.model.Coordinates;
import org.dismefront.model.FromLocation;
import org.dismefront.model.ToLocation;
import java.time.Instant;

@Data
public class RouteResponseDto {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private Instant creationDate;
    private FromLocation from;
    private ToLocation to;
    private Integer distance;
    
    public String toHumanReadableFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("Route Details:\n");
        sb.append("==============\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Distance: ").append(distance).append("\n");
        sb.append("Creation Date: ").append(creationDate != null ? creationDate.toString() : "N/A").append("\n");
        
        if (coordinates != null) {
            sb.append("Coordinates: X=").append(coordinates.getX()).append(", Y=").append(coordinates.getY()).append("\n");
        }
        
        if (from != null) {
            sb.append("From Location: X=").append(from.getX()).append(", Y=").append(from.getY());
            if (from.getZ() != null) {
                sb.append(", Z=").append(from.getZ());
            }
            sb.append("\n");
        }
        
        if (to != null) {
            sb.append("To Location: X=").append(to.getX()).append(", Y=").append(to.getY());
            if (to.getZ() != null) {
                sb.append(", Z=").append(to.getZ());
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}