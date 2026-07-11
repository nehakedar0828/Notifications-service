package com.notifications.notificationsservice.service;

import com.notifications.notificationsservice.dto.NotificationRequest;
import com.notifications.notificationsservice.dto.NotificationResponse;
import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationStatus;
import com.notifications.notificationsservice.exception.NotificationNotFoundException;
import com.notifications.notificationsservice.exception.RateLimitExceededException;
import com.notifications.notificationsservice.kafka.producer.NotificationProducer;
import com.notifications.notificationsservice.repository.NotificationRepository;
import com.notifications.notificationsservice.service.ratelimit.RateLimitService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationProducer producer;
    private final RateLimitService rateLimitService;

    private static final Logger logger =
            LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(
            NotificationRepository repository,
            NotificationProducer producer,
            RateLimitService rateLimitService) {

        this.repository = repository;
        this.producer = producer;
        this.rateLimitService = rateLimitService;
    }

    @Transactional
    public NotificationResponse createNotification(
            NotificationRequest request) {

        if (!rateLimitService.isAllowed(request.getUserId())) {
            logger.warn("Rate limit exceeded for user {}", request.getUserId());
            throw new RateLimitExceededException(
                    request.getUserId()
            );
        }

        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .priority(request.getPriority())
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
                        new NotificationNotFoundException(id));

        return mapToResponse(notification);
    }

    private NotificationResponse mapToResponse(
            Notification notification) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .priority(notification.getPriority())
                .recipient(notification.getRecipient())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .build();
    }
}