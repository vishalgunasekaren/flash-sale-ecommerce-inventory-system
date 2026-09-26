package com.vishal.flashsale.flash_sale_inventory_system.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class TimeConfig {

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}
