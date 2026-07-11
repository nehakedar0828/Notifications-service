package com.notifications.notificationsservice.service.notification;

import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationType;
import com.notifications.notificationsservice.handler.NotificationHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationDispatcher {

    private final List<NotificationHandler> handlers;
    private final Map<NotificationType, NotificationHandler> handlerMap =
            new EnumMap<>(NotificationType.class);

    public NotificationDispatcher(List<NotificationHandler> handlers) {
        this.handlers = handlers;
    }

    @PostConstruct
    public void init() {
        for (NotificationHandler handler : handlers) {
            handlerMap.put(handler.getType(), handler);
        }
    }

    public void dispatch(Notification notification) {

        NotificationHandler handler = handlerMap.get(notification.getType());

        if (handler == null) {
            throw new RuntimeException(
                    "No handler found for notification type: " + notification.getType()
            );
        }

        handler.send(notification);
    }
}