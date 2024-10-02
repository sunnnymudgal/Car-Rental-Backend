package com.project.repos;

import com.project.entities.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends MongoRepository<Customer,String> {
    List<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
}
