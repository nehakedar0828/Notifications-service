package com.notifications.notificationsservice.controller;

import com.notifications.notificationsservice.dto.NotificationRequest;
import com.notifications.notificationsservice.dto.NotificationResponse;
import com.notifications.notificationsservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Notification API",
        description = "Operations for managing notifications"
)
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @Operation(
            summary = "Create a notification",
            description = "Creates a new notification and publishes it to Kafka."
    )
    @PostMapping
    public NotificationResponse create(@Valid @RequestBody NotificationRequest request) {
        return service.createNotification(request);
    }

    @Operation(
            summary = "Get all notifications"
    )
    @GetMapping
    public List<NotificationResponse> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Get notification by ID"
    )
    @GetMapping("/{id}")
    public NotificationResponse getById(@PathVariable Long id){
        return service.getById(id);
    }
}