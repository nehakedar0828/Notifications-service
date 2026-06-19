package com.notifications.notificationsservice.dto;

import com.notifications.notificationsservice.entity.NotificationStatus;
import com.notifications.notificationsservice.entity.NotificationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {

    private Long id;
    private Long userId;
    private NotificationType type;
    private String recipient;
    private String message;
    private NotificationStatus status;
}