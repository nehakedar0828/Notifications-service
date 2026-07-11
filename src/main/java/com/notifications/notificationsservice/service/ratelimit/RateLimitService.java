package com.notifications.notificationsservice.service.ratelimit;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private static final int MAX_REQUESTS = 3;
    private static final long WINDOW_SIZE_MS = 60 * 1000;

    private final Map<Long, RateLimitData> userRequests =
            new ConcurrentHashMap<>();

    public boolean isAllowed(Long userId) {

        long currentTime = System.currentTimeMillis();

        RateLimitData data = userRequests.get(userId);

        if (data == null) {
            userRequests.put(userId, new RateLimitData(1, currentTime));
            return true;
        }

        long windowStart = data.getWindowStart();
        int count = data.getCount();

        if (currentTime - windowStart > WINDOW_SIZE_MS) {
            userRequests.put(userId, new RateLimitData(1, currentTime));
            return true;
        }

        if (count < MAX_REQUESTS) {
            data.setCount(count + 1);
            return true;
        }

        return false;
    }
}