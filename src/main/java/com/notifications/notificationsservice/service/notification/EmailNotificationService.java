package com.notifications.notificationsservice.service.notification;

import com.notifications.notificationsservice.entity.Notification;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationHandler {

    @Override
    public void send(Notification notification) {

        System.out.println(
                "Sending EMAIL to "
                        + notification.getRecipient());

        System.out.println(
                "Message: "
                        + notification.getMessage());
    }
}