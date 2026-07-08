package com.notifications.notificationsservice.service.notification;

import com.notifications.notificationsservice.entity.Notification;

public interface NotificationHandler {

    void send(Notification notification);
}