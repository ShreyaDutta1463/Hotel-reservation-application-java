package ui;

import util.DateUtil;
import api.AdminResource;
import mode1.Customer;
import mode1.IRoom;
import mode1.Room;
import mode1.RoomType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void displayAdminMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1-5.");
            }
        }
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("All Customers:");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println("All Rooms:");
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    private static void seeAllReservations() {
        System.out.println("All Reservations:");
        adminResource.displayAllReservations();
    }

    private static void addRoom() {
        List<IRoom> roomsToAdd = new ArrayList<>();
        boolean adding = true;

        while (adding) {
            System.out.print("Enter room number: ");
            String roomNumber = scanner.nextLine();

            System.out.print("Enter price per night: ");
            double price;
            try {
                price = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a number.");
                continue;
            }

            System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
            String typeInput = scanner.nextLine();
            RoomType roomType = null;
            if (typeInput.equals("1")) {
                roomType = RoomType.SINGLE;
            } else if (typeInput.equals("2")) {
                roomType = RoomType.DOUBLE;
            } else {
                System.out.println("Invalid room type. Please enter 1 or 2.");
                continue;
            }

            roomsToAdd.add(new Room(roomNumber, price, roomType));

            System.out.print("Would you like to add another room? (y/n): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("y")) {
                adding = false;
            }
        }

        adminResource.addRoom(roomsToAdd);
        System.out.println("Rooms successfully added!");
    }
}
