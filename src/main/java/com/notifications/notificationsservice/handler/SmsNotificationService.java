package com.notifications.notificationsservice.handler;

import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService implements NotificationHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(SmsNotificationService.class);

    @Override
    public void send(Notification notification) {

        logger.info(
                "Sending SMS notification | id={} | recipient={} | message={}",
                notification.getId(),
                notification.getRecipient(),
                notification.getMessage()
        );
    }

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }
}