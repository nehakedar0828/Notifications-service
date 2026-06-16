package com.notifications.notificationsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String recipient;
    private String message;
}
