package org.dismefront.controller;

import org.dismefront.dto.RouteListResponseDto;
import org.dismefront.dto.RouteResponseDto;
import org.dismefront.service.NavigatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NavigatorController.class)
public class NavigatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NavigatorService navigatorService;

    @Test
    public void testFindRoutesBetweenLocations() throws Exception {
        // Given
        RouteListResponseDto responseDto = new RouteListResponseDto();
        responseDto.setRoutes(Collections.emptyList());
        when(navigatorService.findRoutesBetweenLocations(anyLong(), anyLong(), anyString(), anyInt(), anyInt()))
                .thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/navigator/routes/1/2/name"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddNewRoute() throws Exception {
        // Given
        RouteResponseDto responseDto = new RouteResponseDto();
        when(navigatorService.addNewRoute(anyLong(), anyLong(), anyInt()))
                .thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/navigator/route/add/1/2/100"))
                .andExpect(status().isCreated());
    }
}