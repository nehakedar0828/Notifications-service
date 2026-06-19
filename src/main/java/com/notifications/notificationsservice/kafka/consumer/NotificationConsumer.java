package com.notifications.notificationsservice.kafka.consumer;

import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationStatus;
import com.notifications.notificationsservice.repository.NotificationRepository;
import com.notifications.notificationsservice.kafka.producer.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final NotificationProducer producer;

    @KafkaListener(
            topics = "notification-topic",
            groupId = "notification-group"
    )
    public void consume(String message){

        Long notificationId = Long.parseLong(message);

        Notification notification = repository.findById(notificationId)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        try{

            System.out.println(
                    "Sending notification to : " +
                            notification.getRecipient()
            );

                /* simulated failure
            if (notification.getRecipient().contains("fail")) {
               throw new RuntimeException("Simulated failure");
           } */

            notification.setStatus(
                    NotificationStatus.DELIVERED
            );

            repository.save(notification);

            System.out.println("Notification Delivered");
        } catch (Exception e){

            Integer retryCount = notification.getRetryCount();

            retryCount++;

            notification.setRetryCount(retryCount);

            if(retryCount >= 3){

                notification.setStatus(
                        NotificationStatus.FAILED
                );

                repository.save(notification);

                System.out.println("Maximum retries reached");
            }else{

                repository.save(notification);

                System.out.println(
                        "Retry attempt : " + retryCount
                );

                producer.sendNotification(
                        notification.getId().toString()
                );
            }
        }
    }
}
