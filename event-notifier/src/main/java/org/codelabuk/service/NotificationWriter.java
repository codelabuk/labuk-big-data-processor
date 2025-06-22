package org.codelabuk.service;

import org.apache.spark.sql.ForeachWriter;
import org.codelabuk.entity.Notification;
import org.codelabuk.jpa.NotificationRepository;
import org.codelabuk.model.NotificationEvent;

import java.time.LocalDateTime;

public class NotificationWriter extends ForeachWriter<NotificationEvent> {

    private final NotificationRepository notificationRepository;

    public NotificationWriter(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public boolean open(long partitionId, long epochId) {
        return true;
    }

    @Override
    public void process(NotificationEvent event) {
        Notification notification = Notification.builder()
                .lakePath(event.getLakePath())
                .sourceSystem(event.getSourceSystem())
                .localDateTime(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void close(Throwable errorOrNull) {
        // cleanup
    }
}
