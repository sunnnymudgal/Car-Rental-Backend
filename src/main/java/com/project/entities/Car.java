package com.project.entities;

import com.project.helper.Car.FuelType;
import com.project.helper.Car.TransmissionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "Car")
public class Car {

    @Id
    private String carID;
    private String model;
    private String brand;
    private String year;
    private String pricePerDay;
    private boolean isAvailable;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private String location;
    private String bookedByCustomerId;


}
