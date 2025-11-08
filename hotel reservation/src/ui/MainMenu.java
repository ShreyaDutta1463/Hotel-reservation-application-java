package ui;

import util.DateUtil;
import java.util.Calendar;
import api.HotelResource;
import mode1.Customer;
import mode1.IRoom;
import mode1.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final HotelResource hotelResource = HotelResource.getInstance();

    public static void displayMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to the Hotel Reservation Application!");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.print("Please select an option: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.displayAdminMenu(); // We'll create AdminMenu next
                    break;
                case "5":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1-5.");
            }
        }
    }

    private static void findAndReserveRoom() {
        try {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            Customer customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println("Customer not found. Please create an account first.");
                return;
            }

            System.out.print("Enter check-in date (dd/MM/yyyy): ");
            Date checkInDate = DateUtil.parseDate(scanner.nextLine());
            if (checkInDate == null) return;

            System.out.print("Enter check-out date (dd/MM/yyyy): ");
            Date checkOutDate = DateUtil.parseDate(scanner.nextLine());
            if (checkOutDate == null) return;

            // Validate check-out is after check-in
            if (!checkOutDate.after(checkInDate)) {
                System.out.println("Check-out date must be after check-in date.");
                return;
            }

            // Try original dates
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

            if (availableRooms.isEmpty()) {
                // No rooms available, calculate +7 days
                Date altCheckIn = DateUtil.addDays(checkInDate, 7);
                Date altCheckOut = DateUtil.addDays(checkOutDate, 7);

                Collection<IRoom> altRooms = hotelResource.findARoom(altCheckIn, altCheckOut);
                if (altRooms.isEmpty()) {
                    System.out.println("No rooms available for your dates or alternative dates.");
                    return;
                }

                System.out.println("\nNo rooms available for your original dates.");
                System.out.println("Recommended rooms for alternative dates:");
                System.out.println("Check-in: " + altCheckIn + ", Check-out: " + altCheckOut);
                for (IRoom room : altRooms) {
                    System.out.println(room);
                }

                System.out.print("Would you like to book on alternative dates? (yes/no): ");
                String choice = scanner.nextLine().toLowerCase();

                if (choice.equals("yes") || choice.equals("y")) {
                    System.out.print("Enter room number to book: ");
                    String roomNumber = scanner.nextLine();

                    IRoom roomToBook = hotelResource.getRoom(roomNumber);
                    if (roomToBook != null && altRooms.contains(roomToBook)) {
                        Reservation reservation = hotelResource.bookARoom(email, roomToBook, altCheckIn, altCheckOut);
                        if (reservation != null) {
                            System.out.println("Booking successful on alternative dates: " + reservation);
                        } else {
                            System.out.println("Failed to book room. It may have been booked by someone else.");
                        }
                    } else {
                        System.out.println("Invalid room number or room not available for alternative dates.");
                    }
                }

            } else {
                // Rooms available for original dates
                System.out.println("\nRooms available for your requested dates:");
                for (IRoom room : availableRooms) {
                    System.out.println(room);
                }

                System.out.print("Enter room number to book: ");
                String roomNumber = scanner.nextLine();

                IRoom roomToBook = hotelResource.getRoom(roomNumber);
                if (roomToBook != null && availableRooms.contains(roomToBook)) {
                    Reservation reservation = hotelResource.bookARoom(email, roomToBook, checkInDate, checkOutDate);
                    if (reservation != null) {
                        System.out.println("Booking successful: " + reservation);
                    } else {
                        System.out.println("Failed to book room. It may have been booked by someone else.");
                    }
                } else {
                    System.out.println("Invalid room number selected.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void seeMyReservations() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("You have no reservations.");
        } else {
            System.out.println("Your reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account successfully created.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    // ----------------------------
    // Main method to run the menu
    // ----------------------------
    public static void main(String[] args) {
        displayMainMenu();
    }
}