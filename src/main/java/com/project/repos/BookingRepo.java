package com.project.repos;

import com.project.entities.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends MongoRepository<Booking,String> {

    List<Booking> findByCarId(String carId);
}
