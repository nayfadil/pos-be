package com.pos.system.addspringbootactuator.service.impl;

import com.pos.system.addspringbootactuator.dto.ActuatorStatusResponse;
import com.pos.system.addspringbootactuator.service.AddSpringBootActuatorService;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddSpringBootActuatorServiceImpl implements AddSpringBootActuatorService {

    private final HealthEndpoint healthEndpoint;

    public AddSpringBootActuatorServiceImpl(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @Override
    public ActuatorStatusResponse getApplicationHealthStatus() {
        HealthComponent healthComponent = healthEndpoint.health();
        Map<String, Object> details = new HashMap<>();
        details.put("details", healthComponent.toString());

        return new ActuatorStatusResponse(
                healthComponent.getStatus().getCode(),
                "pos-be",
                LocalDateTime.now(),
                details
        );
    }
}
