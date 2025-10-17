package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service                        // manage this class as a bean , so this can be injected into controllers/services
public class TelemetryProducer{
    private final KafkaTemplate<String,String> kafkaTemplate;       // this is like a msg dispatcher that sends strings to kafka
    private final Random random = new Random();                     // this will generate a random number   
    private final ObjectMapper objectMapper = new ObjectMapper();   // this will convert java objects to JSON strings


    @Value("${telemetry.topic.name}")
    private String topicName;       // inject the topic name from the config file (application.properties)

    //constructor
    public TelemetryProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;     // spring will inject the kafkaTemplate into this class when creating service
    }


    // this will run every 1 second
    @Scheduled(fixedRate = 1000)
    public void sendRandomTelemetry(){
        try{
            // generating telemetry Data
            Map<String, Object> telemetryData = new HashMap<>();    // create an empty map first
            telemetryData.put("carID", "Aaryan-029");    
            telemetryData.put("speed", 200 + random.nextInt(100));      // this will generate a random number between 200 and 300
            telemetryData.put("rpm", 6000 + random.nextInt(4000));      // this will generate a random number between 6000 and 10000
            telemetryData.put("engineTemp",80 + random.nextInt(40));    
            telemetryData.put("tirePressure", 30 + random.nextInt(10));    
            telemetryData.put("timeStamp", System.currentTimeMillis());    

            String jsonData = objectMapper.writeValueAsString(telemetryData);     // convert the map to JSON string
            kafkaTemplate.send(topicName, jsonData);        // send the JSON string to kafka

            System.out.println("Send data : " + jsonData);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
