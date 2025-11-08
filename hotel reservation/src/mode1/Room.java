package mode1;

public class Room implements IRoom {

    // Variables for the Room
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    // Constructor
    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    // Implementing IRoom methods
    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return this.price == 0.0;
    }

    // Override toString() for better room description
    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                ", Type: " + roomType +
                ", Price: " + (price == 0.0 ? "Free" : "$" + price);
    }
}
