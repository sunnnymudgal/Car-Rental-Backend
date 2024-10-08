package com.project.repos;

import com.project.entities.UserLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepo extends MongoRepository<UserLogin, String> {
    UserLogin findByUsername(String username);
}
