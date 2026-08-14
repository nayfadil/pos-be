package com.pos.system.addspringbootactuator.service.impl;

import com.pos.system.addspringbootactuator.dto.HealthStatusResponse;
import com.pos.system.addspringbootactuator.service.ActuatorService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActuatorServiceImpl implements ActuatorService {

    @Override
    public HealthStatusResponse getCustomHealthStatus() {
        return new HealthStatusResponse(
            "UP",
            "POS System and Spring Boot Actuator monitoring service are healthy",
            LocalDateTime.now()
        );
    }
}