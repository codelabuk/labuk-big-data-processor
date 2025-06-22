package org.codelabuk.processor;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.codelabuk.jpa.NotificationRepository;
import org.codelabuk.model.NotificationEvent;
import org.codelabuk.service.KakfaStreamReader;
import org.codelabuk.service.NotificationWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EventNotificationProcessor implements CommandLineRunner {

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private KakfaStreamReader<NotificationEvent> reader;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void run(String... args) throws Exception {

        Dataset<NotificationEvent> kafkaStream = reader.readAndParse(sparkSession,
                NotificationEvent.class,
                Encoders.bean(NotificationEvent.class));

        StreamingQuery query = kafkaStream.writeStream()
                .foreach(new NotificationWriter(notificationRepository))
                .option("checkPointLocation","/tmp/spark-checkpoint")
                .start();

        query.awaitTermination();
    }
}
