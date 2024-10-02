package com.project.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Customer")
public class Customer {
    @Id
    private String customerId;
    private String name;
    private String email;
    private String driverLicense;
    private String contactInfo;
    private String address;

}
