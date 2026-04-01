package com.hotel.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates:
 * - Read-only search operations
 * - Separation of concerns
 * - Inventory + domain model usage
 *
 * @version 4.0
 */
public class BookMyStayApp {

    // Room Domain Model
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

    // Inventory (from UC3)
    static class RoomInventory {

        private final Map<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 0); // intentionally unavailable
        }

        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        public Map<String, Integer> getAllInventory() {
            return inventory;
        }
    }

    // NEW: Search Service (UC4)
    static class SearchService {

        private final RoomInventory inventory;
        private final Map<String, Room> roomCatalog;

        public SearchService(RoomInventory inventory) {
            this.inventory = inventory;

            // Room details (domain model)
            roomCatalog = new HashMap<>();
            roomCatalog.put("Single Room", new Room("Single Room", 1, 1000));
            roomCatalog.put("Double Room", new Room("Double Room", 2, 2000));
            roomCatalog.put("Suite Room", new Room("Suite Room", 3, 5000));
        }

        // Read-only search
        public void searchAvailableRooms() {
            System.out.println("\nAvailable Rooms:");

            for (String roomType : inventory.getAllInventory().keySet()) {

                int available = inventory.getAvailability(roomType);

                // Filter unavailable rooms
                if (available > 0) {
                    Room room = roomCatalog.get(roomType);
                    room.displayDetails();
                    System.out.println("Available: " + available);
                }
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("       Welcome to Book My Stay");
        System.out.println("       Hotel Booking System v4.0");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        SearchService searchService = new SearchService(inventory);

        // Perform search (READ ONLY)
        searchService.searchAvailableRooms();

        System.out.println("\nApplication execution completed.");
    }
}