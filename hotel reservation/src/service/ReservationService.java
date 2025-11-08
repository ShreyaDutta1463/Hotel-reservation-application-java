

package service;

import mode1.Customer;
import mode1.IRoom;
import mode1.Reservation;

import java.util.*;

public class ReservationService {
    // Collection to store rooms
    private Map<String, IRoom> rooms = new HashMap<>();

    // Collection to store reservations
    private Set<Reservation> reservations = new HashSet<>();

    // Singleton instance
    private static ReservationService instance;

    // Private constructor to prevent external instantiation
    private ReservationService() {}

    // Get the single instance
    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    // 1. Add a room to the hotel
    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    // 2. Get a specific room by its ID
    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    // 3. Reserve a room - made synchronized to handle concurrent access
    public synchronized Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        // Double-check availability (in case of concurrent access)
        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            System.out.println("Room is already booked for these dates.");
            return null;
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    // 4. Find all available rooms for a given date range
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();

        // Check requested dates
        for (IRoom room : rooms.values()) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    // 5. Find alternative rooms for alternative dates
    public Collection<IRoom> findAlternativeRooms(Date checkInDate, Date checkOutDate, int daysToAdd) {
        List<IRoom> availableRooms = new ArrayList<>();

        Date altCheckIn = addDays(checkInDate, daysToAdd);
        Date altCheckOut = addDays(checkOutDate, daysToAdd);

        for (IRoom room : rooms.values()) {
            if (isRoomAvailable(room, altCheckIn, altCheckOut)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    // Helper method to add days to a Date
    private Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    // Helper method to check room availability
    private boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        // Since dates are already normalized by HotelResource, we can use them directly
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                // Check for date overlap
                Date resCheckIn = reservation.getCheckInDate();
                Date resCheckOut = reservation.getCheckOutDate();

                // Check if the requested dates overlap with existing reservation
                // Overlap occurs if:
                // requested check-in is before existing check-out AND
                // requested check-out is after existing check-in
                if (checkInDate.before(resCheckOut) && checkOutDate.after(resCheckIn)) {
                    return false; // room is already booked
                }
            }
        }
        return true;
    }

    // 6. Get all reservations for a particular customer
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    // 7. Print all reservations
    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    // 8. Return all rooms (needed for AdminResource)
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
}