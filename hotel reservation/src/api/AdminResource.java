package api;

import mode1.*;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    // Singleton instance
    private static AdminResource adminResource = null;

    // References to service classes
    private CustomerService customerService;
    private ReservationService reservationService;

    // Private constructor for singleton
    private AdminResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    // Public method to get singleton instance
    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    // Get a customer by email
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    // Add multiple rooms at once
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    // Get all rooms
    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    // Get all customers
    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Display all reservations
    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}