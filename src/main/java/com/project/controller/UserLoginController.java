package com.project.controller;

import com.project.entities.UserLogin;

import com.project.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users") // Base URL for the User operations
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    // Create a new user
    @PostMapping
    public ResponseEntity<UserLogin> createUser(@RequestBody UserLogin userLogin) {
        UserLogin createdUser = userLoginService.createUser(userLogin);
        return ResponseEntity.ok(createdUser);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserLogin>> getAllUsers() {
        List<UserLogin> users = userLoginService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserLogin> getUserById(@PathVariable String id) {
        Optional<UserLogin> user = userLoginService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserLogin> updateUser(@PathVariable String id, @RequestBody UserLogin userLogin) {
        UserLogin updatedUser = userLoginService.updateUser(id, userLogin);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userLoginService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
