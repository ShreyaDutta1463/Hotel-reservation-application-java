package mode1;

import java.util.Date;

public class Reservation {

    // Variables
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    // Constructor
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Getters
    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    // Override toString() for a clear description
    @Override
    public String toString() {
        return "Reservation Details:\n" +
                "Customer: " + customer + "\n" +
                "Room: " + room + "\n" +
                "Check-In Date: " + checkInDate + "\n" +
                "Check-Out Date: " + checkOutDate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return customer.equals(that.customer) &&
                room.equals(that.room) &&
                checkInDate.equals(that.checkInDate) &&
                checkOutDate.equals(that.checkOutDate);
    }

    @Override
    public int hashCode() {
        int result = customer.hashCode();
        result = 31 * result + room.hashCode();
        result = 31 * result + checkInDate.hashCode();
        result = 31 * result + checkOutDate.hashCode();
        return result;
    }
}