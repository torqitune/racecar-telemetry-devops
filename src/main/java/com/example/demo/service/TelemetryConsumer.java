// this file is a consumer class that will listen to the kafka topic called 'xyz-defined-topic-in-config' and reacts to incoming messages


package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.model.Alert;
import com.example.demo.model.TelemetryData;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.TelemetryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;





@Service    // @Service tells spring that this is a service class manage it as a bean
public class TelemetryConsumer{
    private final ObjectMapper objectMapper = new ObjectMapper();   // this mapper setup is used to convert JSON strings to java objects


    
    @Autowired      // @autowired auto-injects dependencies , these lines tells spring that whenver this controller is created , give it ready-to-use intances of TelemetryRepository and ALertRepository
    private TelemetryRepository telemetryRepository;

    @Autowired
    private AlertRepository alertRepository;

    @KafkaListener(topics = "telemetry-data", groupId = "telemetry-group")      // this method will be automatically triggered when a message is received/arrives on the topic , groupID means this consumer is part of a group , useful for load balancing and fault tolerance
    public void consume(String message){
        try {
            TelemetryData data = objectMapper.readValue(message, TelemetryData.class);     // convert the JSON string back to TelemetryData object
            telemetryRepository.save(data);
            System.out.println("Received data : " + data);

            if(data.getEngineTemp() > 100){
                System.out.println("🚨 ALERT: Engine overheating! Temp = " + data.getEngineTemp());
                // This is creating an ALert object
                // data.getEngineTemp() sets the alert type , System.currentTimeMillis() captures the current timestamp in epoch milliseconds
                Alert a = new Alert(data.getCarID(),"Engine_HighTemp","Engine overheating : " + data.getEngineTemp(),System.currentTimeMillis());
                alertRepository.save(a);        // this line persists the alert to the database using Spring Data JPA
            }

            if(data.getTirePressure() < 28 || data.getTirePressure() > 35){     // if the TirePressure is less than 28 or greater than 35 and log an error in the console
                System.out.println("⚠️ ALERT: Tire pressure out of range! Pressure = " + data.getTirePressure());
                Alert a = new Alert(data.getCarID(), "TIRE_PRESSURE","Tire pressure out of range: " + data.getTirePressure(), System.currentTimeMillis());
                alertRepository.save(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}