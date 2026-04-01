package com.hotel.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates:
 * - HashMap for inventory
 * - Encapsulation
 * - Separation of concerns
 *
 * @version 3.0
 */
public class BookMyStayApp {

    // Room Inventory Class (NEW in UC3)
    static class RoomInventory {

        private final Map<String, Integer> inventory;

        // Constructor initializes inventory
        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);
        }

        // Get availability
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        // Book room (reduce count)
        public void bookRoom(String roomType) {
            int available = getAvailability(roomType);

            if (available > 0) {
                inventory.put(roomType, available - 1);
                System.out.println(roomType + " booked successfully!");
            } else {
                System.out.println(roomType + " is not available!");
            }
        }

        // Display inventory
        public void displayInventory() {
            System.out.println("\nCurrent Room Inventory:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("       Welcome to Book My Stay");
        System.out.println("       Hotel Booking System v3.0");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Show initial state
        inventory.displayInventory();

        // Perform booking
        inventory.bookRoom("Single Room");
        inventory.bookRoom("Suite Room");
        inventory.bookRoom("Suite Room");
        inventory.bookRoom("Suite Room"); // exceeds availability

        // Show updated state
        inventory.displayInventory();

        System.out.println("\nApplication execution completed.");
    }
}