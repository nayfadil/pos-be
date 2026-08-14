package com.pos.monitoring.addspringbootactuator.controller;

import com.pos.monitoring.addspringbootactuator.dto.HealthStatusResponse;
import com.pos.monitoring.addspringbootactuator.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/summary")
    public ResponseEntity<HealthStatusResponse> getSystemHealthSummary() {
        return ResponseEntity.ok(monitoringService.getSystemHealthSummary());
    }
}
