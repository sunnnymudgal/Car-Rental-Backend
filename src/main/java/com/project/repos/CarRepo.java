package com.project.repos;

import com.project.entities.Car;
import com.project.helper.Car.FuelType;
import com.project.helper.Car.TransmissionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepo extends MongoRepository<Car,String> {
    Car findCarByBrand(String brand);
    Car findCarByModel(String model);
    List<Car> findByFuelType(FuelType fuelType);
    List<Car> findByTransmissionType(TransmissionType transmissionType);
}
