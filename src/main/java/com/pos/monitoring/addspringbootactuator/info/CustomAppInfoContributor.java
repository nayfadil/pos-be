package com.pos.monitoring.addspringbootactuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAppInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> appDetails = new HashMap<>();
        appDetails.put("name", "POS Backend Application");
        appDetails.put("description", "Point of Sale Enterprise System Monitoring & API Services");
        appDetails.put("version", "1.0.0");
        
        builder.withDetail("app", appDetails);
    }
}
