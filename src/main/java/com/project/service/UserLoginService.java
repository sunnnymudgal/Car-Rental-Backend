package com.project.service;

import com.project.entities.UserLogin;
import com.project.repos.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLoginService {

    @Autowired
    private UserLoginRepo userLoginRepository;

    // Create a new user
    public UserLogin createUser(UserLogin userLogin) {
        return userLoginRepository.save(userLogin);
    }

    // Get all users
    public List<UserLogin> getAllUsers() {
        return userLoginRepository.findAll();
    }

    // Get user by ID
    public Optional<UserLogin> getUserById(String id) {
        return userLoginRepository.findById(id);
    }

    // Update user
    public UserLogin updateUser(String id, UserLogin userLogin) {
        userLogin.setId(id);
        return userLoginRepository.save(userLogin);
    }

    // Delete user
    public void deleteUser(String id) {
        userLoginRepository.deleteById(id);
    }
}
