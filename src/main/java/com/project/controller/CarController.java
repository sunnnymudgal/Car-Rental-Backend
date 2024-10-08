package com.project.controller;

import com.project.entities.Car;
import com.project.helper.Car.FuelType;
import com.project.helper.Car.TransmissionType;
import com.project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    // Get all cars
    @GetMapping("/getInfo")
    public ResponseEntity<List<Car>> getCars() {
        List<Car> cars = carService.getAllCarsInfo();

        for (Car car : cars) {
            // Assuming car.getImage() returns binary data (byte[])
            byte[] imageBytes = car.getImage();
            if (imageBytes != null && imageBytes.length > 0) {
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                car.setBase64Image(base64Image); // Set the encoded string in the base64Image field
            }
        }

        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // Add a new car
    @PostMapping("/setInfo")
    public ResponseEntity<Car> setCar(
            @RequestParam("model") String model,
            @RequestParam("brand") String brand,
            @RequestParam("year") String year,
            @RequestParam("pricePerDay") String pricePerDay,
            @RequestParam("isAvailable") boolean isAvailable,
            @RequestParam("fuelType") FuelType fuelType,
            @RequestParam("transmissionType") TransmissionType transmissionType,
            @RequestParam("location") String location,
            @RequestParam(value = "bookedByCustomerId", required = false) String bookedByCustomerId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile // Image upload
    ) {
        try {
            Car car = new Car();
            car.setModel(model);
            car.setBrand(brand);
            car.setYear(year);
            car.setPricePerDay(pricePerDay);
            car.setAvailable(isAvailable);
            car.setFuelType(fuelType);
            car.setTransmissionType(transmissionType);
            car.setLocation(location);
            car.setBookedByCustomerId(bookedByCustomerId);

            Car savedCar = carService.addCarInfo(car, imageFile);
            System.out.println("Received car data: " + model + ", " + brand + ", " + year + ", " + pricePerDay);

            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable String id) {
        Optional<Car> carData = carService.getCarById(id);
        return carData.map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete car by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable String id) {
        boolean isDeleted = carService.deleteCarById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find car by brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<Car> getCarByBrand(@PathVariable String brand) {
        Car car = carService.findCarByBrand(brand);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Find car by model
    @GetMapping("/model/{model}")
    public ResponseEntity<Car> getCarByModel(@PathVariable String model) {
        Car car = carService.findCarByModel(model);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Find by FuelType
    @GetMapping("/fuelType/{fuelType}")
    public ResponseEntity<List<Car>> getCarsByFuelType(@PathVariable FuelType fuelType) {
        List<Car> cars = carService.findCarsByFuelType(fuelType);
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // Find by TransmissionType
    @GetMapping("/transmissionType/{transmissionType}")
    public ResponseEntity<List<Car>> getCarsByTransmissionType(@PathVariable TransmissionType transmissionType) {
        List<Car> cars = carService.findCarsByTransmissionType(transmissionType);
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // Update a car by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable("id") String carId, @RequestBody Car updatedCar) {
        try {
            Car car = carService.updateCar(carId, updatedCar);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
