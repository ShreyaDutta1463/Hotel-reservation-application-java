package mode1;

public class FreeRoom extends Room {

    // Constructor - price is always set to 0.0
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType); // Price is fixed at 0
    }

    // Override toString() for a clearer description
    @Override
    public String toString() {
        return "Free Room -> " + super.toString();
    }
}
