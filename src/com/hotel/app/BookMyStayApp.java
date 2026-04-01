package com.hotel.app;

// ---------------- NEW: CONCURRENT BOOKING PROCESSOR ----------------
class ConcurrentBookingProcessor implements Runnable {

    private final BookingQueue queue;
    private final BookingService bookingService;

    public ConcurrentBookingProcessor(BookingQueue queue, BookingService bookingService) {
        this.queue = queue;
        this.bookingService = bookingService;
    }

    @Override
    public void run() {
        process();
    }

    // Critical section
    private void process() {

        while (true) {
            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) return;
                r = queue.getNext();
            }

            // synchronized booking to prevent race condition
            synchronized (bookingService) {
                bookingService.processSingleBooking(r);
            }
        }
    }
}