package com.notifications.notificationsservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotBlank
    private String recipient;

    @NotBlank
    private String message;
}
