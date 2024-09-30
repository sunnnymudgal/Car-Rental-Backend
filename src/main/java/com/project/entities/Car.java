package com.project.entities;

import com.project.helper.Car.FuelType;
import com.project.helper.Car.TransmissionType;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
public class Car {
    @Id
    private String CarID;
    private String Model;
    private String	Brand;
    private String	Year;
    private String	PricePerDay;
    private boolean	isAvailable;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private String	Location;

}
