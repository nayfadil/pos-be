package com.pos.system.addspringbootactuator.controller;

import com.pos.system.addspringbootactuator.dto.HealthStatusResponse;
import com.pos.system.addspringbootactuator.service.ActuatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/actuator-status")
public class ActuatorCustomController {

    private final ActuatorService actuatorService;

    public ActuatorCustomController(ActuatorService actuatorService) {
        this.actuatorService = actuatorService;
    }

    @GetMapping
    public ResponseEntity<HealthStatusResponse> getStatus() {
        return ResponseEntity.ok(actuatorService.getCustomHealthStatus());
    }
}