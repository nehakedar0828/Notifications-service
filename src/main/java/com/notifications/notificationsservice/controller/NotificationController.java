package com.notifications.notificationsservice.controller;

import com.notifications.notificationsservice.dto.NotificationRequest;
import com.notifications.notificationsservice.dto.NotificationResponse;
import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.repository.NotificationRepository;
import com.notifications.notificationsservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public NotificationResponse create(@Valid @RequestBody NotificationRequest request) {
        return service.save(request);
    }

    @GetMapping
    public List<NotificationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public NotificationResponse getById(@PathVariable Long id){
        return service.getById(id);
    }
}