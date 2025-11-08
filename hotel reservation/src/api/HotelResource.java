package api;

import mode1.*;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.Calendar;

public class HotelResource {

    // Singleton instance
    private static HotelResource hotelResource = null;

    // References to service classes
    private CustomerService customerService;
    private ReservationService reservationService;

    // Private constructor for singleton
    private HotelResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    // Public method to get singleton instance
    public static HotelResource getInstance() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    // Get a customer by email
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    // Create a new customer
    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    // Get a room by room number
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    // Book a room for a customer
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = customerService.getCustomer(customerEmail);
        if (customer == null) {
            System.out.println("Customer not found for email: " + customerEmail);
            return null;
        }

        // Normalize dates before booking
        Date normalizedCheckIn = normalizeDate(checkInDate);
        Date normalizedCheckOut = normalizeDate(checkOutDate);

        return reservationService.reserveARoom(customer, room, normalizedCheckIn, normalizedCheckOut);
    }

    // Get all reservations for a customer
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        if (customer == null) {
            return null;
        }
        return reservationService.getCustomersReservation(customer);
    }

    // Find all available rooms for a date range
    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        // Normalize dates before checking availability
        Date normalizedCheckIn = normalizeDate(checkInDate);
        Date normalizedCheckOut = normalizeDate(checkOutDate);
        return reservationService.findRooms(normalizedCheckIn, normalizedCheckOut);
    }

    // Helper method to normalize dates (set time to midnight)
    private Date normalizeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
