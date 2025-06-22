package org.codelabuk.config;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("prefix = notification.kafka")
@Data
public class NotificationKafkaConfig {

    private String bootstrapServer;
    private String topic;
    private String startingOffsets = "latest";

}
