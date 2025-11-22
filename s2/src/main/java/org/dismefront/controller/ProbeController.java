package org.dismefront.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/live")
public class ProbeController {
    @GetMapping()
    public ResponseEntity<String> isAlive() {
        return ResponseEntity.ok("The service is alive");
    }
}
