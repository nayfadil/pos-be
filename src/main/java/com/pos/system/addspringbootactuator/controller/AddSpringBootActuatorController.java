package com.pos.system.addspringbootactuator.controller;

import com.pos.system.addspringbootactuator.dto.ActuatorStatusResponse;
import com.pos.system.addspringbootactuator.service.AddSpringBootActuatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system/actuator")
public class AddSpringBootActuatorController {

    private final AddSpringBootActuatorService actuatorService;

    public AddSpringBootActuatorController(AddSpringBootActuatorService actuatorService) {
        this.actuatorService = actuatorService;
    }

    @GetMapping("/status")
    public ResponseEntity<ActuatorStatusResponse> getStatus() {
        return ResponseEntity.ok(actuatorService.getApplicationHealthStatus());
    }
}
