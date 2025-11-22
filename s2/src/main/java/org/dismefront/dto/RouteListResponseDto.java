package org.dismefront.dto;

import lombok.Data;
import java.util.List;

@Data
public class RouteListResponseDto {
    private List<RouteResponseDto> routes;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
    
    public String toTableFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s %-20s %-10s %-20s%n", "ID", "Name", "Distance", "Creation Date"));
        sb.append("------------------------------------------------------------\n");
        
        if (routes != null && !routes.isEmpty()) {
            for (RouteResponseDto route : routes) {
                String name = route.getName() != null ? route.getName() : "N/A";
                // Truncate name if too long
                if (name.length() > 20) {
                    name = name.substring(0, 17) + "...";
                }
                String creationDate = route.getCreationDate() != null ? 
                    route.getCreationDate().toString() : "N/A";
                if (creationDate.length() > 20) {
                    creationDate = creationDate.substring(0, 17) + "...";
                }
                sb.append(String.format("%-5d %-20s %-10d %-20s%n", 
                    route.getId(), name, route.getDistance(), creationDate));
            }
        } else {
            sb.append("No routes found.\n");
        }
        
        sb.append("\nPage ").append(currentPage + 1).append(" of ").append(totalPages);
        sb.append(" (Total: ").append(totalElements).append(" routes)");
        
        return sb.toString();
    }
}