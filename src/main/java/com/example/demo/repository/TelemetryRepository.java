package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.TelemetryData;

// interface is the blueprint of a class
// an interface typically contains method signatures but does not contains method bodies

public interface TelemetryRepository extends JpaRepository<TelemetryData, Long> {       // this class extends JpaRepository which gives functions like save(), findById(), delete(), findAll(). Here TelemetryData is the enity type and Long is the type of primary key
    
    // following are derived queries , i.e spring boot will auto generate SQL based on the method names
    
    TelemetryData findTopByOrderByTimeStampDesc();      // fetch the recent telemtry record
    TelemetryData findTopByCarIDOrderByTimeStampDesc(String carID);     // fetch the recent telemtry record for given car
    List<TelemetryData> findByCarID(String carId);
    List<TelemetryData> findByTimeStampBetween(Long start, Long end);
    Page<TelemetryData> findAll(Pageable pageable); // already provided by JpaRepository but fine to reference
}
