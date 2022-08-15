package com.autorush.rushchat;

import com.autorush.rushchat.config.kafka.KafkaConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {KafkaConstants.class})
public class RushchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(RushchatApplication.class, args);
    }

}
