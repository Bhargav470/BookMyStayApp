package com.hotel.app;

import java.util.*;

/**
 * Book My Stay Application
 * Use Case 5: Booking Request (FIFO Queue)
 *
 * Demonstrates:
 * - Queue (FIFO)
 * - Fair request handling
 * - Separation of request & allocation
 *
 * @version 5.0
 */
public class BookMyStayApp {

    // ---------------- ROOM DOMAIN ----------------
    static class Room {
        private final String type;
        private final int beds;
        private final double price;

        public Room(String type, int beds, double price) {
            this.type = type;
            this.beds = beds;
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void displayDetails() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    // ---------------- INVENTORY ----------------
    static class RoomInventory {
        private final Map<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 0);
        }

        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        public Map<String, Integer> getAllInventory() {
            return inventory;
        }
    }

    // ---------------- SEARCH SERVICE (UC4) ----------------
    static class SearchService {
        private final RoomInventory inventory;
        private final Map<String, Room> roomCatalog;

        public SearchService(RoomInventory inventory) {
            this.inventory = inventory;

            roomCatalog = new HashMap<>();
            roomCatalog.put("Single Room", new Room("Single Room", 1, 1000));
            roomCatalog.put("Double Room", new Room("Double Room", 2, 2000));
            roomCatalog.put("Suite Room", new Room("Suite Room", 3, 5000));
        }

        public void searchAvailableRooms() {
            System.out.println("\nAvailable Rooms:");

            for (String roomType : inventory.getAllInventory().keySet()) {
                int available = inventory.getAvailability(roomType);

                if (available > 0) {
                    Room room = roomCatalog.get(roomType);
                    room.displayDetails();
                    System.out.println("Available: " + available);
                }
            }
        }
    }

    // ---------------- NEW: RESERVATION ----------------
        record Reservation(String guestName, String roomType) {

        public void display() {
                System.out.println("Guest: " + guestName + " requested " + roomType);
            }
        }

    // ---------------- NEW: BOOKING REQUEST QUEUE ----------------
    static class BookingRequestQueue {

        private Queue<Reservation> queue;

        public BookingRequestQueue() {
            queue = new LinkedList<>();
        }

        // Add request (FIFO)
        public void addRequest(Reservation reservation) {
            queue.offer(reservation);
            System.out.println("Request added to queue:");
            reservation.display();
        }

        // View all requests (read-only)
        public void showAllRequests() {
            System.out.println("\nBooking Request Queue:");

            for (Reservation r : queue) {
                r.display();
            }
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("       Welcome to Book My Stay");
        System.out.println("       Hotel Booking System v5.0");
        System.out.println("=====================================");

        // UC4: Search
        RoomInventory inventory = new RoomInventory();
        SearchService searchService = new SearchService(inventory);
        searchService.searchAvailableRooms();

        // UC5: Booking Requests
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Show queue (FIFO order)
        bookingQueue.showAllRequests();

        System.out.println("\nNOTE: No booking processed yet. Only requests collected.");
        System.out.println("Application execution completed.");
    }
}