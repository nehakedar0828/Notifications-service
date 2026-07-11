package com.notifications.notificationsservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI notificationAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Notification Service API")
                                .description("Event-driven Notification Service using Spring Boot, Kafka, and PostgreSQL")
                                .version("1.0")
                );
    }
}