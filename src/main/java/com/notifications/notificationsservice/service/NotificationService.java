package com.notifications.notificationsservice.service;

import com.notifications.notificationsservice.dto.NotificationRequest;
import com.notifications.notificationsservice.dto.NotificationResponse;
import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationStatus;
import com.notifications.notificationsservice.kafka.producer.NotificationProducer;
import com.notifications.notificationsservice.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationProducer producer;

    public NotificationService(
            NotificationRepository repository,
            NotificationProducer producer) {

        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public NotificationResponse save(
            NotificationRequest request) {

        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .recipient(request.getRecipient())
                .message(request.getMessage())
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .build();

        notification = repository.save(notification);

        producer.sendNotification(
                notification.getId().toString());

        return mapToResponse(notification);
    }

    public List<NotificationResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public NotificationResponse getById(Long id) {

        Notification notification = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        return mapToResponse(notification);
    }

    private NotificationResponse mapToResponse(
            Notification notification) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .recipient(notification.getRecipient())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .build();
    }
}