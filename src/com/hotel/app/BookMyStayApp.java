package com.hotel.app;

// ---------------- NEW: CANCELLATION SERVICE ----------------
class CancellationService {

    private final RoomInventory inventory;
    private final Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void cancel(Reservation r) {

        if (r == null || r.roomId == null) {
            System.out.println("❌ Invalid cancellation request");
            return;
        }

        // Push to stack (LIFO)
        rollbackStack.push(r.roomId);

        // Restore inventory
        inventory.increment(r.roomType);

        System.out.println("🔄 Booking Cancelled:");
        System.out.println("Room ID Released: " + r.roomId);
    }
}