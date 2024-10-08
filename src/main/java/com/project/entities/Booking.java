package com.project.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("Booking")
@Data
public class Booking {
    @Id
    private String bookingId;
    private String customerId;
    private String carId;
    private Date bookingDate;
    private Date startDate;
    private Date endDate;
    private String totalCost;

}
