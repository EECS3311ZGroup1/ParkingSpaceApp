package yorku.eecs3311.parking;

import java.time.LocalDate;
import java.util.*;

public class ParkingSpace {
	
	private int spaceID;
    private String spaceLocation;
    private boolean isEnabled;
    private final Map<String, List<TimeSlot>> availableTimeSlots;
	private Sensor sensor;
	
	public ParkingSpace(int spaceID, String lotName) {
        this.spaceID = spaceID;
        this.spaceLocation = lotName + spaceID;
        this.isEnabled = true;
        this.availableTimeSlots = new HashMap<>();
        initializeSlots();
        sensor = new Sensor();
    }
	
	// Initialize 8 time slots per day for 5 days
	private void initializeSlots() {
        for (int i = 0; i < 5; i++) { // Generate slots for 5 days ahead
            String date = LocalDate.now().plusDays(i).toString(); // Example: "2025-03-20"
            List<TimeSlot> slots = new ArrayList<>();
            
            for (int hour = 8; hour < 18; hour++) { // 8AM - 5PM for 1 hour intervals
                slots.add(new TimeSlot(hour + ":00"));
            }
            
            availableTimeSlots.put(date, slots);
        }
    }
	
	// Get available time slots for a specific day
	public List<TimeSlot> getSlotsForDate(String date) {
        if (!isEnabled) { return new ArrayList<>(); }
        return availableTimeSlots.get(date);
	}
    
    // Book multiple slots
	public boolean bookSlots(String date, List<String> times) {
	    if (!isEnabled) return false;

	    List<TimeSlot> slots = availableTimeSlots.get(date);
	    if (slots == null) return false;

	    boolean booked = false;
	    for (TimeSlot slot : slots) {
	        if (times.contains(slot.getTime()) && slot.isAvailable()) {
	            slot.setBooked(true);
	            booked = true;
	        }
	    }
	    
	    System.out.println("\n[*] Booking " + times + " on " + date);
	    return booked;
	}
	
	// Get all available dates
	public List<String> getAvailableDates() {
		List<String> sortedDates = new ArrayList<>(availableTimeSlots.keySet());
	    Collections.sort(sortedDates); // Sort in ascending order
	    return sortedDates;
	}
	
	// Enable or disable a space
	public void enable() { isEnabled = true; }
    public void disable() { isEnabled = false; }

	// Getters
    public int getSpaceID() { return spaceID; }
	public String getSpaceLocation() { return spaceLocation; }
	public boolean isEnabled() { return isEnabled; }
 	
	// Sensor for management purposes
	public boolean isCarParked() { return sensor.isCarParked(); }
	
	@Override
	public String toString() {
	    return "Space ID: " + spaceID + " (" + spaceLocation + ") - " + (isEnabled ? "Enabled" : "Disabled");
	}
	
}
