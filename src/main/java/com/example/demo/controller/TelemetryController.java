package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Alert;
import com.example.demo.model.TelemetryData;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.TelemetryRepository;



@RestController     // marking this class as restcontroller , since it combines controller + responseBody , returns JSON directly
@RequestMapping("/api")     // all endpoints in this class will be prefixed with /api
@CrossOrigin(origins="*")       // this enables CORS for all origins
public class TelemetryController {
    @Autowired                      // Autowired injects spring managed bean , so that we can access it anywhere
    private TelemetryRepository telemetryRepository;

    @Autowired
    private AlertRepository alertRepository;

    @GetMapping("/telemetry")                      
    public List<TelemetryData> getTelemetry(
        @RequestParam(required=false) String carId,     // @RequestParam tells spring that boot to bind the query parameter  from the URL to a method argument
        @RequestParam(required = false) Long start,     // for eg, if the client sends a GET request to GET /api/telemetry?carId=CAR_42 , then this line will bind "CAR_42" to the carId variable
        @RequestParam(required = false) Long end,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "50") int size){


            if(carId != null){      // if carId is provided then fetch all 
                return telemetryRepository.findByCarID(carId);
            }
            if(start != null && end != null){   //if time range is provided , fetch telemetry data within that interval
                return telemetryRepository.findByTimeStampBetween(start, end);
            }
            // default : paginated all , newest first , i.e sorted by timeStamp
            return telemetryRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timeStamp"))).getContent();
        }

    @GetMapping("/telemetry/latest")    // this maps the HTTP get request to /api/telemetry/latest
    public ResponseEntity<TelemetryData> getLatestTelemetry(@RequestParam(required=false)   String carId){      // ResponseEntity<TelemetryData> wraps the response with HTTP status code and body
        TelemetryData t;
        if (carId != null) {    // if carID is provided then fetch the latest telemetry entry for that specific car
            t = telemetryRepository.findTopByCarIDOrderByTimeStampDesc(carId);
        } else {    // else if not provided , fetch latest telemetry across all cars
            t = telemetryRepository.findTopByOrderByTimeStampDesc();
        }
        if(t == null)   return ResponseEntity.noContent().build();  // if no telemetry data is found then return HTTP 204 - no data , 
        return ResponseEntity.ok(t);    // else if telementry data was found return with HTTP 200 code
    }

    // GET /api/alerts
    @GetMapping("/alerts")      // maps http get request to /api/alerts
    public List<Alert> getAlerts(@RequestParam(required = false) String carId) {
        if (carId != null) {
            return alertRepository.findByCarId(carId);
        }
        return alertRepository.findAll();
    }

    

}
