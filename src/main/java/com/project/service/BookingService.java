package com.project.service;

import com.project.entities.Booking;
import com.project.entities.Car;
import com.project.entities.Customer;
import com.project.repos.BookingRepo;
import com.project.repos.CarRepo;
import com.project.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CarRepo carRepo;

    public Booking createBooking(String customerId, String carId, Booking bookingDetails) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (car.isAvailable()) {
            bookingDetails.setCarId(carId);
            car.setAvailable(false);

            car.setBookedByCustomerId(customerId);

            carRepo.save(car);
        } else {
            throw new RuntimeException("Car is not available");
        }

        bookingDetails.setCustomerId(customerId);

        bookingDetails.setBookingDate(new Date());

        Booking savedBooking = bookingRepo.save(bookingDetails);

        if (customer.getBookings() == null) {
            customer.setBookings(new ArrayList<>());
        }
        customer.getBookings().add(savedBooking);
        customerRepo.save(customer);

        return savedBooking;
    }

    public String deleteBooking(String bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        String carId = booking.getCarId();
        String customerId = booking.getCustomerId();

        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));


        car.setAvailable(true);
        car.setBookedByCustomerId(null);
        carRepo.save(car);


        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));


        customer.getBookings().removeIf(bookingItem -> bookingItem.getBookingId().equals(bookingId));
        customerRepo.save(customer);
        bookingRepo.deleteById(bookingId);

        return "Booking with ID: " + bookingId + " has been deleted successfully.";
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public List<Booking> getBookingsByCarId(String carId) {
        return bookingRepo.findByCarId(carId);
    }
}
