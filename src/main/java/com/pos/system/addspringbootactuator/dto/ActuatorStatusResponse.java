package com.pos.system.addspringbootactuator.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ActuatorStatusResponse(
    String status,
    String applicationName,
    LocalDateTime timestamp,
    Map<String, Object> details
) {}
