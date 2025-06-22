package org.codelabuk.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.codelabuk.config.NotificationKafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.spark.api.java.function.MapFunction;

@Service
public class KakfaStreamReader<T> {

    @Autowired
    private NotificationKafkaConfig kafkaConfig;

    @Autowired
    private ObjectMapper objectMapper;


    public Dataset<T> readAndParse(SparkSession sparkSession, Class<T> clazz, Encoder<T> encoder) {
        return  sparkSession.readStream()
                .format("kafka")
                .option("kafka.bootstrap.server", kafkaConfig.getBootstrapServer())
                .option("subscribe",kafkaConfig.getTopic())
                .option("startingOffsets", kafkaConfig.getStartingOffsets())
                .load()
                .selectExpr("CAST(value AS STRING)")
                .as(Encoders.STRING())
                .map((MapFunction<String, T>) json -> objectMapper.readValue(json, clazz), encoder);

    }

}
