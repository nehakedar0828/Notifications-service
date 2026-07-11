package com.notifications.notificationsservice.handler;

import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService implements NotificationHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(PushNotificationService.class);

    @Override
    public void send(Notification notification) {

        logger.info(
                "Sending PUSH notification | id={} | recipient={} | message={}",
                notification.getId(),
                notification.getRecipient(),
                notification.getMessage()
        );
    }

    @Override
    public NotificationType getType() {
        return NotificationType.PUSH;
    }
}