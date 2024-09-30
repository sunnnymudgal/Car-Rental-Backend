package com.project.service;

import com.project.entities.Car;
import com.project.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService{

    @Autowired
    private CarRepo carRepo;

    public List<Car> getAllCarsInfo(){
        return carRepo.findAll();
    }

    public Car addCarInfo(Car car){
        return carRepo.save(car);
    }
}
