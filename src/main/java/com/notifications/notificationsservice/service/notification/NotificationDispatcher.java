package com.notifications.notificationsservice.service.notification;

import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationDispatcher {

    private final EmailNotificationService emailService;
    private final SmsNotificationService smsService;
    private final PushNotificationService pushService;

    public void dispatch(Notification notification) {

        if (notification.getType() == NotificationType.EMAIL) {
            emailService.send(notification);
        } else if (notification.getType() == NotificationType.SMS) {
            smsService.send(notification);
        } else if (notification.getType() == NotificationType.PUSH) {
            pushService.send(notification);
        }
    }
}