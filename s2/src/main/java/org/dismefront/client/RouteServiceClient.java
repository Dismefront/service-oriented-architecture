package org.dismefront.client;

import org.dismefront.model.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.List;

@Component
public class RouteServiceClient {

    @Value("${route.service.url:https://localhost:23151/lab2/api/v1}")
    private String routeServiceUrl;

    private final RestTemplate restTemplate;

    public RouteServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Route> getAllRoutes(int page, int size, String sort, String filter) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(routeServiceUrl + "/routes")
                .queryParam("page", page)
                .queryParam("size", size);

        if (sort != null && !sort.isEmpty()) {
            builder.queryParam("sort", sort);
        }

        String builderUriString = builder.toUriString();

        // КОСТЫЫЫЛЬ
        if (filter != null && !filter.isEmpty()) {
            builderUriString += "&filter=" + filter;
        }

        ResponseEntity<Route[]> response = restTemplate.getForEntity(
                builderUriString,
                Route[].class
        );

        return Arrays.asList(response.getBody());
    }

    public Route getRouteById(Long id) {
        String url = routeServiceUrl + "/routes/{id}";
        ResponseEntity<Route> response = restTemplate.getForEntity(url, Route.class, id);
        return response.getBody();
    }

    public Route createRoute(Long idFrom, Long idTo, Integer distance) {
        String url = routeServiceUrl + "/routes/add/{fromId}/{toId}/{distance}";

        ResponseEntity<Route> response = restTemplate.postForEntity(
                url,
                null, Route.class,
                idFrom, idTo, distance);
        return response.getBody();
    }

    public Route updateRoute(Long id, Route route) {
        String url = routeServiceUrl + "/routes/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Route> request = new HttpEntity<>(route, headers);
        
        ResponseEntity<Route> response = restTemplate.exchange(
                url, HttpMethod.PUT, request, Route.class, id);
        return response.getBody();
    }

    public void deleteRoute(Long id) {
        String url = routeServiceUrl + "/routes/{id}";
        restTemplate.delete(url, id);
    }

    public List<Route> searchRoutesByName(String substring) {
        String url = routeServiceUrl + "/routes/search/{substring}";
        ResponseEntity<Route[]> response = restTemplate.getForEntity(url, Route[].class, substring);
        return Arrays.asList(response.getBody());
    }

    public List<Integer> getUniqueDistances() {
        String url = routeServiceUrl + "/routes/unique-distances";
        ResponseEntity<Integer[]> response = restTemplate.getForEntity(url, Integer[].class);
        return Arrays.asList(response.getBody());
    }

    public void deleteRouteByDistance(Integer distance) {
        String url = routeServiceUrl + "/routes/by-distance/{distance}";
        restTemplate.delete(url, distance);
    }
}