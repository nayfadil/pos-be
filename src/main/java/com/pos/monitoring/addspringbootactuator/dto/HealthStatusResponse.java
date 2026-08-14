package com.pos.monitoring.addspringbootactuator.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record HealthStatusResponse(
    String status,
    LocalDateTime timestamp,
    Map<String, Object> details
) {}