package com.notifications.notificationsservice.dto;

import com.notifications.notificationsservice.entity.NotificationPriority;
import com.notifications.notificationsservice.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequest {

    private Long userId;

    private NotificationType type;

    @NotBlank
    private String recipient;

    @NotBlank
    private String message;

    private NotificationPriority priority;
}