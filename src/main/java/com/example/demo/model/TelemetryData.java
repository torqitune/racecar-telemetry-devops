package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity     // this marks this class as a JPA entity , which means that this class will represent a table in the database. (Spring boot + JPA will automatically map this class to a table and manage it using the Hibernate(ORM engine))
@Table(name = "telemetry_data")     // this annotation will mark that this class is a JPA entity and this will tell JPA that to map this entity to a table called telemetry_data else it will map it to a table called TelemetryData by default
public class TelemetryData {

    @Id     // this marks id as the primary key of the table
    @GeneratedValue(strategy=GenerationType.IDENTITY)       // this tells JPA to auto generate the ID , i.e auto increment
    private Long id;

    private int speed;          // these are private fields
    private int rpm;            // these variables holds the telemetry data
    private String carID;
    private long timeStamp;
    private int engineTemp;
    private int tirePressure;

    // Add a no-args constructor (needed for Jackson) , jackson helps convert JSON to java objects in kafka
    public TelemetryData() {
    }

    // constructor 
    public TelemetryData(int speed, int rpm, int engineTemp, int tirePressure, long timeStamp, String carID) {
        this.speed = speed;         // initialize the the object with values passed during creation
        this.rpm = rpm;             // here 'this' refers to the class field
        this.tirePressure = tirePressure;
        this.timeStamp = timeStamp;
        this.engineTemp = engineTemp;
        this.carID = carID;


    }

    // defining getter methods below and setter
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) { this.speed = speed; }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm){this.rpm = rpm;}

    public double getTirePressure() {
        return tirePressure;
    }

    public void setTirePresssure(int tirePressure){this.tirePressure = tirePressure;}

    public int getEngineTemp() { return engineTemp; }

    public void setEngineTemp(int engineTemp) { this.engineTemp = engineTemp; }

    public long getTimeStamp() { return timeStamp; }
    public void setTimeStamp(long timeStamp) { this.timeStamp = timeStamp; }

    public String getCarID() { return carID; }
    public void setCarID(String carID) { this.carID = carID; }






    @Override       // this method is meant to override the default toString method , we do this so that any mistype/missconfigure the method name or signature , java will throw error i.e compile time error
    public String toString() {
        return "TelemetryData{" +
                "timeStamp=" + timeStamp +
                ", engineTemp=" + engineTemp +
                ", tirePressure=" + tirePressure +
                ", speed=" + speed +
                ", rpm=" + rpm +
                ", carID='" + carID + '\'' +
                '}';   // this object will be returned
    }
}
