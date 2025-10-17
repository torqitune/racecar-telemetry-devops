// this code defines a spring Data JPA respository interface named AlertRepository which provides database access method for Alert entity.

package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Alert;



// interface is like a contract , we define what methods a class must implement but now how they're implemented
// analogy : a TV remote has function like powerOn() , volUp(), changeChannel() etc. , it doen't care how TV turns on or change channel , just defined what actions are avlb , so now a Samsung or SOny tv both can implement the same remote interface and internally they may use logic to respond to the same remote functions

public interface AlertRepository extends JpaRepository<Alert, Long> {       // creating a repository Alert entity , primary key type is Long , and by extending JpaRepository we get save() , findbyId(), etc

    List<Alert> findByCarId(String carId);  // this is derived query method , i.e Spring auto-generates SQl based on the method name , it fetches all alerts where carID field matches the given value.
    
}
