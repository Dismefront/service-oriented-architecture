package org.dismefront.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    @GetMapping("/live")
    public ResponseEntity<String> livenessProbe() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/ready")
    public ResponseEntity<String> readinessProbe() {
        return ResponseEntity.ok("READY");
    }
}