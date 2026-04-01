package com.hotel.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Book My Stay Application
 * A simple Hotel Booking Management System
 * Demonstrates Core Java + Data Structures (ArrayList)
 *
 * Features:
 * - Room initialization
 * - View available rooms
 * - Book rooms
 * - Prevent double booking
 *
 * @author Bhargav
 * @version 1.0
 */
public class BookMyStayApp {

    // Room class (inner class for simplicity)
    static class Room {
        private final int roomNumber;
        private final String type;
        private boolean isAvailable;

        public Room(int roomNumber, String type) {
            this.roomNumber = roomNumber;
            this.type = type;
            this.isAvailable = true;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void bookRoom() {
            isAvailable = false;
        }

        @Override
        public String toString() {
            return "Room " + roomNumber + " (" + type + ") - " +
                    (isAvailable ? "Available" : "Booked");
        }
    }

    // Booking class
    static class Booking {
        private final String customerName;
        private final int roomNumber;

        public Booking(String customerName, int roomNumber) {
            this.customerName = customerName;
            this.roomNumber = roomNumber;
        }

        @Override
        public String toString() {
            return customerName + " booked Room " + roomNumber;
        }
    }

    // Data Structures
    private static final List<Room> rooms = new ArrayList<>();
    private static final List<Booking> bookings = new ArrayList<>();

    // Initialize rooms
    public static void initializeRooms() {
        rooms.add(new Room(101, "Single"));
        rooms.add(new Room(102, "Double"));
        rooms.add(new Room(103, "Deluxe"));
        rooms.add(new Room(104, "Suite"));
    }

    // Show available rooms
    public static void showAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    // Book room
    public static void bookRoom(String customerName, int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (room.isAvailable()) {
                    room.bookRoom();
                    bookings.add(new Booking(customerName, roomNumber));
                    System.out.println("Booking successful for " + customerName);
                } else {
                    System.out.println("Room " + roomNumber + " is already booked!");
                }
                return;
            }
        }
        System.out.println("Room not found!");
    }

    // Show all bookings
    public static void showBookings() {
        System.out.println("\nBooking Records:");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    // Main method (entry point)
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("       Welcome to Book My Stay");
        System.out.println("       Hotel Booking System v1.0");
        System.out.println("=====================================");

        // Initialize system
        initializeRooms();

        // Show rooms before booking
        showAvailableRooms();

        // Perform bookings
        bookRoom("Arun", 101);
        bookRoom("Rahul", 102);
        bookRoom("Amit", 101); // Attempt double booking

        // Show updated rooms
        showAvailableRooms();

        // Show booking records
        showBookings();

        System.out.println("\nApplication execution completed.");
    }
}