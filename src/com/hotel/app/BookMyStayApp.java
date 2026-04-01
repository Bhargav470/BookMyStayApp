package com.hotel.app;

/**
 * Book My Stay Application
 * Use Case 2: Basic Room Types & Static Availability
 *
 * @version 2.1
 */
public class BookMyStayApp {

    // Abstract Room class
    abstract static class Room {
        protected String type;
        protected int beds;
        protected double price;

        public Room(String type, int beds, double price) {
            this.type = type;
            this.beds = beds;
            this.price = price;
        }

        public abstract void display();
    }

    // Single Room
    static class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1, 1000);
        }

        public void display() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    // Double Room
    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 2000);
        }

        public void display() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    // Suite Room
    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 5000);
        }

        public void display() {
            System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
        }
    }

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("       Welcome to Book My Stay");
        System.out.println("       Hotel Booking System v2.1");
        System.out.println("=====================================");

        // Create room objects (polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        boolean isSingleAvailable = true;
        boolean isDoubleAvailable = false;
        boolean isSuiteAvailable = true;

        System.out.println("\nRoom Details & Availability:");

        single.display();
        System.out.println("Available: " + (isSingleAvailable ? "Yes" : "No"));

        doubleRoom.display();
        System.out.println("Available: " + (isDoubleAvailable ? "Yes" : "No"));

        suite.display();
        System.out.println("Available: " + (isSuiteAvailable ? "Yes" : "No"));

        System.out.println("\nApplication execution completed.");
    }
}