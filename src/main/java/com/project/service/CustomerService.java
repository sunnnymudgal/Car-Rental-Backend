package com.project.service;

import com.project.entities.Booking;
import com.project.entities.Customer;
import com.project.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private BookingService bookingService;

    // Get all customers
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    //get Customer by id
    public Optional<Customer> getCustomerById(String id){
        return customerRepo.findById(id);
    }

    // Add a new customer
    public void addCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    // Find customers by name
    public List<Customer> findByName(String name) {
        return customerRepo.findByName(name);
    }

    // Find customer by email
    public Optional<Customer> findByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    // Update an existing customer by customerId
    public Customer updateCustomer(String customerId, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOpt = customerRepo.findById(customerId);

        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();

            // Update fields only if they are provided
            existingCustomer.setName(updatedCustomer.getName() != null && !updatedCustomer.getName().isEmpty() ? updatedCustomer.getName() : existingCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isEmpty() ? updatedCustomer.getEmail() : existingCustomer.getEmail());
            existingCustomer.setDriverLicense(updatedCustomer.getDriverLicense() != null && !updatedCustomer.getDriverLicense().isEmpty() ? updatedCustomer.getDriverLicense() : existingCustomer.getDriverLicense());
            existingCustomer.setContactInfo(updatedCustomer.getContactInfo() != null && !updatedCustomer.getContactInfo().isEmpty() ? updatedCustomer.getContactInfo() : existingCustomer.getContactInfo());
            existingCustomer.setAddress(updatedCustomer.getAddress() != null && !updatedCustomer.getAddress().isEmpty() ? updatedCustomer.getAddress() : existingCustomer.getAddress());

            return customerRepo.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    // Delete a customer by customerId
    public void deleteCustomer(String customerId) {
        // Find the customer by ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Get the list of bookings associated with the customer
        List<Booking> customerBookings = customer.getBookings();

        // Delete each booking by calling the deleteBooking method from BookingService
        if (customerBookings != null) {
            for (Booking booking : customerBookings) {
                bookingService.deleteBooking(booking.getBookingId()); // Reuse deleteBooking logic
            }
        }

        // Delete the customer after removing bookings
        customerRepo.deleteById(customerId);
    }
}
