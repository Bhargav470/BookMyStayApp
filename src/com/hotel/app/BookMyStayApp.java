package com.hotel.app;

// ---------------- NEW: BOOKING HISTORY ----------------
class BookingHistory {

    private final List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }
}

// ---------------- NEW: REPORT SERVICE ----------------
class ReportService {

    public void generateReport(List<Reservation> history) {

        System.out.println("\n📊 Booking Report:");

        for (Reservation r : history) {
            System.out.println("Guest: " + r.guestName +
                    " | Room: " + r.roomType +
                    " | ID: " + r.roomId);
        }

        System.out.println("Total Bookings: " + history.size());
    }
}