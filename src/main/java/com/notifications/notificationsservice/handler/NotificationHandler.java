package com.notifications.notificationsservice.handler;

import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationType;

public interface NotificationHandler {

    void send(Notification notification);

    NotificationType getType();
}