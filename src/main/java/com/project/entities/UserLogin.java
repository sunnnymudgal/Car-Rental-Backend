package com.project.entities;

import lombok.Data;
import org.springframework.data.annotation.Id; // Import Id annotation
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserLogin {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;
}
