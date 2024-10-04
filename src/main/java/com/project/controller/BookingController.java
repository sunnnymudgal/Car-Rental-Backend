package com.project.controller;

import com.project.entities.Booking;
import com.project.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking bookingDetails) {
        try {
            Booking createdBooking = bookingService.createBooking(bookingDetails.getCustomerId(), bookingDetails.getCarId(), bookingDetails);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Booking>> getBookingsByCarId(@PathVariable String carId) {
        List<Booking> bookings = bookingService.getBookingsByCarId(carId);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable String bookingId) {
        try {
            String responseMessage = bookingService.deleteBooking(bookingId);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
        }
    }
}
