package com.notifications.notificationsservice.controller;

import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.repository.NotificationRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository repository;

    public NotificationController(NotificationRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Notification create(@Valid @RequestBody Notification notification) {
        return repository.save(notification);
    }

    @GetMapping
    public List<Notification> getAll() {
        return repository.findAll();
    }
}