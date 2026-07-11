package com.notifications.notificationsservice.service.ratelimit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateLimitData {
    private int count;
    private long windowStart;
}