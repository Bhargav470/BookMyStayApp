package com.hotel.app;

// ---------------- NEW: CUSTOM EXCEPTION ----------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}