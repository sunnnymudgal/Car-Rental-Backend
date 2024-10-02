package com.project.service;

import com.project.entities.Car;
import com.project.helper.Car.FuelType;
import com.project.helper.Car.TransmissionType;
import com.project.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepo carRepo;

    // Get all cars
    public List<Car> getAllCarsInfo() {
        try {
            List<Car> cars = carRepo.findAll();
            System.out.println("Retrieved " + cars.size() + " cars from the repository.");
            return cars;
        } catch (Exception e) {
            System.err.println("Error occurred while retrieving cars: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve cars", e); // Rethrow as a runtime exception
        }
    }


    // Add a new car
    public Car addCarInfo(Car car) {
        // Check if the provided car object is null
        if (car == null) {
            throw new IllegalArgumentException("Car object must not be null");
        }

        // Check for required fields (example: model and brand)
        if (car.getModel() == null || car.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Car model must not be null or empty");
        }
        if (car.getBrand() == null || car.getBrand().trim().isEmpty()) {
            throw new IllegalArgumentException("Car brand must not be null or empty");
        }

        // Additional validation for other fields can be added here

        try {
            Car savedCar = carRepo.save(car);
            System.out.println("Car added successfully: " + savedCar);
            return savedCar;
        } catch (Exception e) {
            System.err.println("Error occurred while adding the car: " + e.getMessage());
            throw new RuntimeException("Failed to add car", e); // Rethrow as a runtime exception
        }
    }


    // Get car by ID
    public Optional<Car> getCarById(String id) {
        // Check if the provided ID is null or empty
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Car ID must not be null or empty");
        }

        Optional<Car> carOptional = carRepo.findById(id);

        // Log the result
        if (carOptional.isPresent()) {
            System.out.println("Car found with ID " + id + ": " + carOptional.get());
        } else {
            System.out.println("No car found with ID " + id);
        }

        return carOptional;
    }


    // Delete car by ID
    public boolean deleteCarById(String id) {
        // Check if the provided ID is null or empty
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Car ID must not be null or empty");
        }

        try {
            // Check if the car exists before trying to delete
            Optional<Car> existingCar = carRepo.findById(id);
            if (existingCar.isPresent()) {
                carRepo.deleteById(id);
                System.out.println("Car with ID " + id + " has been deleted successfully.");
                return true;
            } else {
                System.out.println("No car found with ID " + id + " to delete.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error occurred while trying to delete car with ID " + id + ": " + e.getMessage());
            return false;
        }
    }


    // Find car by brand
    public Car findCarByBrand(String brand) {
        // Check if the provided brand is null or empty
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand must not be null or empty");
        }

        Car car = carRepo.findCarByBrand(brand);

        // Check if the car was found and log the result
        if (car == null) {
            System.out.println("No car found for brand: " + brand);
        } else {
            System.out.println("Car found for brand: " + brand + " - " + car);
        }

        return car;
    }


    // Find car by model
    public Car findCarByModel(String model) {
        // Check if the provided model is null or empty
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model must not be null or empty");
        }

        Car car = carRepo.findCarByModel(model);

        // Check if the car was found and log the result
        if (car == null) {
            System.out.println("No car found for model: " + model);
        } else {
            System.out.println("Car found for model: " + model + " - " + car);
        }

        return car;
    }


    // Find by FuelType
    public List<Car> findCarsByFuelType(FuelType fuelType) {
        // Check if the provided fuel type is null
        if (fuelType == null) {
            throw new IllegalArgumentException("Fuel type must not be null");
        }

        List<Car> cars = carRepo.findByFuelType(fuelType);

        // Check if any cars were found and log the results
        if (cars.isEmpty()) {
            System.out.println("No cars found for fuel type: " + fuelType);
        } else {
            System.out.println(cars.size() + " cars found for fuel type: " + fuelType);
        }

        return cars;
    }


    // Find by TransmissionType
    public List<Car> findCarsByTransmissionType(TransmissionType transmissionType) {
        // Check if the provided transmission type is null
        if (transmissionType == null) {
            throw new IllegalArgumentException("Transmission type must not be null");
        }

        List<Car> cars = carRepo.findByTransmissionType(transmissionType);

        // Check if any cars were found and return a meaningful response
        if (cars.isEmpty()) {
            System.out.println("No cars found for transmission type: " + transmissionType);
        } else {
            System.out.println(cars.size() + " cars found for transmission type: " + transmissionType);
        }

        return cars;
    }


    // Update Car Info
    public Car updateCar(String carId, Car updatedCar) {
        Optional<Car> existingCarOptional = carRepo.findById(carId);

        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();

            // Update fields only if they are provided
            existingCar.setBrand(updatedCar.getBrand() != null && !updatedCar.getBrand().isEmpty() ? updatedCar.getBrand() : existingCar.getBrand());
            existingCar.setModel(updatedCar.getModel() != null && !updatedCar.getModel().isEmpty() ? updatedCar.getModel() : existingCar.getModel());
            existingCar.setYear(updatedCar.getYear() != null && !updatedCar.getYear().isEmpty() ? updatedCar.getYear() : existingCar.getYear());
            existingCar.setPricePerDay(updatedCar.getPricePerDay() != null && !updatedCar.getPricePerDay().isEmpty() ? updatedCar.getPricePerDay() : existingCar.getPricePerDay());
            existingCar.setAvailable(updatedCar.isAvailable() != existingCar.isAvailable() ? updatedCar.isAvailable() : existingCar.isAvailable()); // Boolean field
            existingCar.setFuelType(updatedCar.getFuelType() != null ? updatedCar.getFuelType() : existingCar.getFuelType());
            existingCar.setTransmissionType(updatedCar.getTransmissionType() != null ? updatedCar.getTransmissionType() : existingCar.getTransmissionType());
            existingCar.setLocation(updatedCar.getLocation() != null && !updatedCar.getLocation().isEmpty() ? updatedCar.getLocation() : existingCar.getLocation());

            // Save the updated car back to the repository
            return carRepo.save(existingCar);
        } else {
            throw new RuntimeException("Car with ID " + carId + " not found");
        }
    }


}
