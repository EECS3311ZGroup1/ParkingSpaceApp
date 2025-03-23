package yorku.eecs3311.parking;

public class TimeSlot {
	
	private final String time; // like 7:00 AM
    private boolean isAvailable;

    public TimeSlot(String time) {
        this.time = time;
        this.isAvailable = true;
    }

    public String getTime() {
        return time;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setBooked(boolean booked) {
        this.isAvailable = !booked;
    }
    
    public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

    @Override
    public String toString() {
        return time + (isAvailable ? " (Available)" : " (Booked)");
    }

}
