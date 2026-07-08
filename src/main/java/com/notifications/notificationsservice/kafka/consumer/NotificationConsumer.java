package com.notifications.notificationsservice.kafka.consumer;

import com.notifications.notificationsservice.entity.Notification;
import com.notifications.notificationsservice.entity.NotificationStatus;
import com.notifications.notificationsservice.repository.NotificationRepository;
import com.notifications.notificationsservice.kafka.producer.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.notifications.notificationsservice.service.notification.NotificationDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final NotificationProducer producer;
    private final NotificationDispatcher dispatcher;

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
            processByPriority(notification);

            dispatcher.dispatch(notification);

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

    private void processByPriority(Notification notification)
            throws InterruptedException {

        switch (notification.getPriority()) {

            case HIGH:
                System.out.println(
                        "Processing HIGH priority notification..."
                );
                break;

            case MEDIUM:
                System.out.println(
                        "Processing MEDIUM priority notification..."
                );
                Thread.sleep(1000);
                break;

            case LOW:
                System.out.println(
                        "Processing LOW priority notification..."
                );
                Thread.sleep(2000);
                break;
        }
    }
}
