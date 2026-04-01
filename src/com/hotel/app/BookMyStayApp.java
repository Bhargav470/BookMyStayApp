package com.hotel.app;

import java.util.*;

/**
 * UC6: Reservation Confirmation & Room Allocation
 * @version 6.0
 */
public class BookMyStayApp {

    // ---------------- INVENTORY ----------------
    static class RoomInventory {
        private final Map<String, Integer> inventory = new HashMap<>();

        public RoomInventory() {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 2);
        }

        public int getAvailability(String type) {
            return inventory.getOrDefault(type, 0);
        }

        public void decrement(String type) {
            inventory.put(type, getAvailability(type) - 1);
        }
    }

    // ---------------- RESERVATION ----------------
    static class Reservation {
        String guestName;
        String roomType;
        String roomId; // assigned after booking

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    // ---------------- QUEUE ----------------
    static class BookingQueue {
        Queue<Reservation> queue = new LinkedList<>();

        public void add(Reservation r) {
            queue.offer(r);
        }

        public Reservation getNext() {
            return queue.poll();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // ---------------- BOOKING SERVICE (UC6) ----------------
    static class BookingService {

        private final RoomInventory inventory;

        // Prevent duplicate room IDs
        private final Set<String> allocatedRoomIds = new HashSet<>();

        // Track room allocations
        private final Map<String, Set<String>> roomAllocMap = new HashMap<>();

        public BookingService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        // Process queue
        public void processBookings(BookingQueue queue) {

            while (!queue.isEmpty()) {

                Reservation r = queue.getNext();

                int available = inventory.getAvailability(r.roomType);

                if (available > 0) {

                    String roomId = generateRoomId(r.roomType);

                    // Assign room
                    r.roomId = roomId;
                    allocatedRoomIds.add(roomId);

                    roomAllocMap
                            .computeIfAbsent(r.roomType, k -> new HashSet<>())
                            .add(roomId);

                    // Update inventory
                    inventory.decrement(r.roomType);

                    System.out.println("✅ Booking Confirmed:");
                    System.out.println("Guest: " + r.guestName);
                    System.out.println("Room Type: " + r.roomType);
                    System.out.println("Room ID: " + r.roomId);
                    System.out.println("----------------------");

                } else {
                    System.out.println("❌ No rooms available for " + r.roomType);
                }
            }
        }

        // Generate unique ID
        private String generateRoomId(String type) {
            String id;
            do {
                id = type.substring(0, 2).toUpperCase() + new Random().nextInt(1000);
            } while (allocatedRoomIds.contains(id));

            return id;
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Add requests (UC5)
        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Single Room"));
        queue.add(new Reservation("Charlie", "Single Room")); // exceeds

        // UC6 Processing
        BookingService bookingService = new BookingService(inventory);
        bookingService.processBookings(queue);
    }
}