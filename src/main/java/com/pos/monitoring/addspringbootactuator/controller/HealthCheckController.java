package com.pos.monitoring.addspringbootactuator.controller;

import com.pos.monitoring.addspringbootactuator.dto.HealthStatusResponse;
import com.pos.monitoring.addspringbootactuator.service.HealthCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/monitoring")
public class HealthCheckController {

    private final HealthCheckService healthCheckService;

    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/health-summary")
    public ResponseEntity<HealthStatusResponse> getHealthSummary() {
        return ResponseEntity.ok(healthCheckService.getApplicationHealthDetails());
    }
}