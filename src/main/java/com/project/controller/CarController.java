package com.project.controller;

import com.project.entities.Car;
import com.project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/getInfo")
    public List<Car> getCars(){
        return carService.getAllCarsInfo();
    }

    @PostMapping("/setInfo")
    public Car setCar(@RequestBody Car car){
        return carService.addCarInfo(car);
    }

}
