package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity     // marking this class as an entity , i.e map java classes to databases tables
@Table(name="alerts")       // Maps this entity to alerts table in database
public class Alert {
    @Id         // mark Id as primary key
    @GeneratedValue(strategy=GenerationType.IDENTITY)       // use auto-incrementing Ids
    private Long id;
    private String carId;
    private String type;     // e.g. "ENGINE_OVERTEMP", "TIRE_PRESSURE"
    private String message;
    private Long timeStamp;

    public Alert(){}    // default constructor


    // this is an parameterized constructor , using here to initialize the objects
    public Alert(String carId , String type , String message, Long timeStamp){
        this.carId = carId;
        this.type = type;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    // defining getter and setter
    public Long getId() {return id;}
    public String getCarId(){return carId;}
    public void setCarID(String carId){this.carId = carId;}
    public String getType(){return type;}
    public void setType(String type){this.type = type;}
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getTimeStamp() { return timeStamp; }
    public void setTimeStamp(Long timeStamp) { this.timeStamp = timeStamp; }
    
}


