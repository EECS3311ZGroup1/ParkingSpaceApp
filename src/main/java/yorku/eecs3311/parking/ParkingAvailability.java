package yorku.eecs3311.parking;

import java.time.LocalDate;
import java.util.*;

public class ParkingAvailability {
	
	private final Map<LocalDate, List<TimeSlot>> availableSlots;

    public ParkingAvailability() {
        this.availableSlots = new HashMap<>();
        initializeSlots();
    }

    // Generate 15 hourly slots from 7 AM - 10 PM for each day
    private void initializeSlots() {
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) { // Next 7 days
            LocalDate date = today.plusDays(i);
            List<TimeSlot> slots = new ArrayList<>();
            for (int hour = 7; hour < 22; hour++) { // 7 AM - 10 PM
                slots.add(new TimeSlot(hour + ":00"));
            }
            availableSlots.put(date, slots);
        }
    }

    // Get available time slots for a selected date
    public List<TimeSlot> getSlotsForDate(LocalDate date) {
        return availableSlots.getOrDefault(date, new ArrayList<>());
    }

    // Book multiple time slots
    public boolean bookSlots(LocalDate date, List<String> times) {
        List<TimeSlot> slots = availableSlots.get(date);
        if (slots != null) {
            for (TimeSlot slot : slots) {
                if (times.contains(slot.getTime()) && slot.isAvailable()) {
                    slot.setBooked(true);
                }
            }
            return true;
        }
        return false;
    }
    
    // TEST
    public static void main(String[] args) {
        ParkingAvailability availability = new ParkingAvailability();
        LocalDate selectedDate = LocalDate.now().plusDays(2); // Example date

        // Display available slots
        System.out.println("Available slots for " + selectedDate + ":");
        for (TimeSlot slot : availability.getSlotsForDate(selectedDate)) {
            System.out.println(slot);
        }

        // Book multiple slots
        List<String> selectedSlots = Arrays.asList("9:00", "10:00", "11:00");
        if (availability.bookSlots(selectedDate, selectedSlots)) {
            System.out.println("\nSlots booked successfully: " + selectedSlots);
        } else {
            System.out.println("\nSlots could not be booked.");
        }

        // Show updated slots
        System.out.println("\nUpdated slots for " + selectedDate + ":");
        for (TimeSlot slot : availability.getSlotsForDate(selectedDate)) {
            System.out.println(slot);
        }
    }
	
}
