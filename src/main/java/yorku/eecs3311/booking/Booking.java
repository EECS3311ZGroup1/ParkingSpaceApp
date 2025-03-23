package yorku.eecs3311.booking;

import java.util.List;

import yorku.eecs3311.parking.ParkingSpace;
import yorku.eecs3311.parking.TimeSlot;

public class Booking {

	private int bookingID;
	private String lotName;
	private int spaceID;
	private String date;
	private int startHour;
	private int dur;
	private String payment;
	
	private boolean isExtended;
	private boolean isCancelled;
	private boolean isCheckedOut;
	private double deposit;
	private String email;
	private double finalAmountCharged;
	
	public Booking(int bookingID, String lotName, int spaceID, 
				   String date, int startHour, int dur,
				   String payment, double deposit, String email, 
				   boolean isExtended, boolean isCancelled, boolean isCheckedOut) {
		this.bookingID = bookingID;
		this.lotName = lotName;
		this.spaceID = spaceID;
		this.date = date;
		this.startHour = startHour;
		this.dur = dur;
		this.payment = payment;
		this.deposit = deposit;
		this.email = email;
		this.isExtended = isExtended;
		this.isCancelled = isCancelled;
		this.isCheckedOut = isCheckedOut;
	}
	
	/**
     * Call this method when user checks out or cancels.
     */
    public double calculateFinalCharge() {
        if (isCancelled) {
            finalAmountCharged = -deposit; // refund
        } else if (isCheckedOut) {
            double total = deposit * dur;
            finalAmountCharged = total - deposit; // minus deposit already paid
        } else {
            finalAmountCharged = 0;
        }
        return finalAmountCharged;
    }
    
    /*
     * Extend handling
     */
    public boolean canExtend(int extraHours, String date, ParkingSpace space) {
        List<TimeSlot> slots = space.getSlotsForDate(date);
        if (slots == null) return false;

        for (int i = 0; i < extraHours; i++) {
            int hourToCheck = startHour + dur + i;
            String slotTime = hourToCheck + ":00";
            boolean found = false;
            for (TimeSlot ts : slots) {
                if (ts.getTime().equals(slotTime) && ts.isAvailable()) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public boolean extend(int extraHours, String date, ParkingSpace space) {
        if (!canExtend(extraHours, date, space)) return false;

        // Mark new time slots as booked
        List<TimeSlot> slots = space.getSlotsForDate(date);
        for (int i = 0; i < extraHours; i++) {
            int hourToBook = startHour + dur + i;
            String slotTime = hourToBook + ":00";
            for (TimeSlot ts : slots) {
                if (ts.getTime().equals(slotTime)) {
                    ts.setBooked(true);
                    break;
                }
            }
        }

        dur += extraHours;
        isExtended = true;
        return true;
    }
    
    // Setters for status changes
    public void cancel() { isCancelled = true; }
    public void checkout() { isCheckedOut = true; }
    public void setFinalAmountCharged(double finalCharge) {
		this.finalAmountCharged = finalCharge;
	}
	
	// Getters
	public int getBookingID() { return bookingID; }
	public String getLotName() { return lotName; }
	public int getSpaceID() { return spaceID; }
	public String getDate() { return date; }
	public int getStartHour() { return startHour; }
	public int getDur() { return dur; }
	public String getPaymentMethod() { return payment; }
	public double getDeposit() { return deposit; }
	public String getEmail() { return email; }
    public double getFinalAmountCharged() { return finalAmountCharged; }
    public boolean isCancelled() { return isCancelled; }
    public boolean isCheckedOut() { return isCheckedOut; }
    public boolean isExtended() { return isExtended; }

	@Override
	public String toString() {
		return "Booking [bookingID=" + bookingID + ", lotName=" + lotName + ", spaceID=" + spaceID + ", startHour="
				+ startHour + ", dur=" + dur + ", paidBy: " + payment + "]";
	}

}
