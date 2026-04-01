package com.hotel.app;

import java.util.*;

/**
 * UC7: Add-On Service Selection
 * @version 7.0
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
        String roomId;

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
        private final Set<String> allocatedRoomIds = new HashSet<>();
        private final List<Reservation> confirmedReservations = new ArrayList<>();

        public BookingService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        public List<Reservation> getConfirmedReservations() {
            return confirmedReservations;
        }

        public void processBookings(BookingQueue queue) {

            while (!queue.isEmpty()) {

                Reservation r = queue.getNext();

                if (inventory.getAvailability(r.roomType) > 0) {

                    String roomId = generateRoomId(r.roomType);

                    r.roomId = roomId;
                    allocatedRoomIds.add(roomId);
                    inventory.decrement(r.roomType);

                    confirmedReservations.add(r);

                    System.out.println("✅ Confirmed: " + r.guestName + " -> " + r.roomId);

                } else {
                    System.out.println("❌ No rooms available for " + r.roomType);
                }
            }
        }

        private String generateRoomId(String type) {
            String id;
            do {
                id = type.substring(0, 2).toUpperCase() + new Random().nextInt(1000);
            } while (allocatedRoomIds.contains(id));

            return id;
        }
    }

    // ---------------- NEW: SERVICE ----------------
    static class Service {
        String name;
        double cost;

        public Service(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
    }

    // ---------------- NEW: SERVICE MANAGER ----------------
    static class ServiceManager {

        // Map<ReservationID, List<Service>>
        private Map<String, List<Service>> serviceMap = new HashMap<>();

        // Add service to reservation
        public void addService(String roomId, Service service) {
            serviceMap
                    .computeIfAbsent(roomId, k -> new ArrayList<>())
                    .add(service);

            System.out.println("➕ Service added to " + roomId + ": " + service.name);
        }

        // Calculate total service cost
        public double calculateCost(String roomId) {
            List<Service> services = serviceMap.getOrDefault(roomId, new ArrayList<>());

            double total = 0;
            for (Service s : services) {
                total += s.cost;
            }
            return total;
        }

        // Display services
        public void showServices(String roomId) {
            System.out.println("\nServices for " + roomId + ":");

            List<Service> services = serviceMap.get(roomId);

            if (services == null) {
                System.out.println("No services selected.");
                return;
            }

            for (Service s : services) {
                System.out.println("- " + s.name + " (₹" + s.cost + ")");
            }

            System.out.println("Total Add-On Cost: ₹" + calculateCost(roomId));
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Double Room"));

        BookingService bookingService = new BookingService(inventory);
        bookingService.processBookings(queue);

        // UC7: Add-on services
        ServiceManager serviceManager = new ServiceManager();

        List<Reservation> confirmed = bookingService.getConfirmedReservations();

        if (!confirmed.isEmpty()) {
            String roomId = confirmed.get(0).roomId;

            serviceManager.addService(roomId, new Service("WiFi", 200));
            serviceManager.addService(roomId, new Service("Breakfast", 500));

            serviceManager.showServices(roomId);
        }
    }
}