package com.project.entities;

import org.springframework.stereotype.Component;

@Component("Booking")
public class Booking {

    private String bookingId;
    private String customerId;
    private String bookingDate;
    private String startDate;
    private String endDate;
    private String totalCost;

}
