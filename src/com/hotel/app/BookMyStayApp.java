package com.hotel.app;

// ---------------- NEW: PERSISTENCE SERVICE ----------------
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    // SAVE
    public void save(RoomInventory inventory, List<Reservation> history) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            oos.writeObject(history);

            System.out.println("💾 State saved successfully.");

        } catch (Exception e) {
            System.out.println("⚠️ Save failed: " + e.getMessage());
        }
    }

    // LOAD
    public Object[] load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory inventory = (RoomInventory) ois.readObject();
            List<Reservation> history = (List<Reservation>) ois.readObject();

            System.out.println("♻️ State restored successfully.");

            return new Object[]{inventory, history};

        } catch (Exception e) {
            System.out.println("⚠️ No previous state found. Starting fresh.");
            return null;
        }
    }
}