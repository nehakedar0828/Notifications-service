package com.notifications.notificationsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class NotificationsServiceApplication {

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        System.out.println("Timezone = " + TimeZone.getDefault().getID());

        SpringApplication.run(
                NotificationsServiceApplication.class,
                args
        );
    }
}