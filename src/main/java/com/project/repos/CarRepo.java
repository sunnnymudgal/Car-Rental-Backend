package com.project.repos;

import com.project.entities.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends MongoRepository<Car,String> {
}
