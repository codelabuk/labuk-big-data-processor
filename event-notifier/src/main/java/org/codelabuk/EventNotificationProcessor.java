package org.codelabuk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventNotificationProcessor implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EventNotificationProcessor.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
