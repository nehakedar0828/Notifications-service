package com.notifications.notificationsservice.exception;

public class RateLimitExceededException
        extends RuntimeException {

    public RateLimitExceededException(Long userId) {
        super("Rate limit exceeded for user: " + userId);
    }
}