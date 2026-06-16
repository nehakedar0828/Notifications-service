package com.notifications.notificationsservice.service;

import com.notifications.notificationsservice.dto.NotificationRequest;
import com.notifications.notificationsservice.dto.NotificationResponse;
import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository){
        this.repository = repository;
    }

    public NotificationResponse save(NotificationRequest request){

        Notification notification = new Notification();

        notification.setRecipient(request.getRecipient());
        notification.setMessage(request.getMessage());

        Notification saved = repository.save(notification);

        return new NotificationResponse(
                saved.getId(),
                saved.getRecipient(),
                saved.getMessage()
        );
    }

    public List<NotificationResponse> getAll(){
        return repository.findAll()
                .stream()
                .map(notification -> new NotificationResponse(
                        notification.getId(),
                        notification.getRecipient(),
                        notification.getMessage()
                ))
                .collect(Collectors.toList());
    }

    public NotificationResponse getById(Long id){

        Notification notification = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        return new NotificationResponse(
                notification.getId(),
                notification.getRecipient(),
                notification.getMessage()
        );
    }
}
