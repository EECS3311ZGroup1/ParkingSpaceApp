package yorku.eecs3311.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import yorku.eecs3311.Database;
import yorku.eecs3311.booking.Booking;
import yorku.eecs3311.booking.BookingBuilder;
import yorku.eecs3311.manager.ManagerAccount;
import yorku.eecs3311.parking.ParkingLot;
import yorku.eecs3311.parking.ParkingSpace;
import yorku.eecs3311.payment.CreditCard;
import yorku.eecs3311.payment.DebitCard;
import yorku.eecs3311.payment.MobilePayment;
import yorku.eecs3311.payment.PaymentStrategy;

public class ModelBooking {
	
	private Database database = Database.getInstance();
	
	// Booking
	public boolean bookAParking(String spaceLocation, String date, String time, 
								String duration, String plate, String payment, 
								double deposit, String email) {
		try {
			// Get the parking lot and space
	        String lotName = spaceLocation.substring(0, 1);
	        int spaceID = Integer.parseInt(spaceLocation.substring(1));
	        
	        ParkingLot targetLot = ManagerAccount.getLotByName(lotName);
	        ParkingSpace targetSpace = targetLot.getSpaceById(spaceID);
	        
	        // Generate time slots
	        int startHour = Integer.parseInt(time.split(":")[0]);
	        int dur = Integer.parseInt(duration);
	        List<String> toBook = new ArrayList<>();
	        
	        for (int i = 0; i < dur; i++) {
	            toBook.add((startHour + i) + ":00");
	        }

	        // Book time slots
	        targetSpace.bookSlots(date, toBook);
	        
	        // Create booking
	        int bookingID = new Random().nextInt(90000) + 10000;
	        
	        Booking booking = new BookingBuilder()
	        		.setBookingID(bookingID)
	        		.setLotName(lotName)
	        		.setSpaceID(spaceID)
	        		.setDate(date)
	        		.setStartHour(startHour)
	        		.setDur(dur)
	        		.setPaymentMethod(payment)
	        		.setDeposit(deposit)
	        		.setEmail(email)
	        		.isExtended(false)
	        		.isCancelled(false)
	        		.isCheckedOut(false)
	        		.build();
	        
	        database.addBookingToDatabase(booking);
	        System.out.println("\n[+] Booking Created: ID #" + bookingID + " | " + booking);
	        
		} catch (Exception e) {
			System.out.println("\n[-] Error booking a parking: " + e.getMessage());
		}
		
		return true;
	}
	
	// Get un-cancelled un-checkedout bookings by email
	public List<Booking> getUCUCBookingsByEmail(String email) {
		return database.getUCUCBookingsByEmail(email);
	}
	
	// Get un-extended (UCUC included) bookings by email
	public List<Booking> getUEBookingsByEmail(String email) {
		return database.getUEBookingsByEmail(email);
	}
	
	// Extend a booking
	public boolean extendABooking(Booking booking, int extraHours) {
	    LocalDate today = LocalDate.now();
	    LocalTime now = LocalTime.now();
	    LocalDate bookedDate = LocalDate.parse(booking.getDate());
	    int expiryHour = booking.getStartHour() + booking.getDur();
	    LocalTime expiryTime = LocalTime.of(expiryHour, 0);

	    // 1. Check if we are before expiry
	    if (today.isBefore(bookedDate) || 
	       (today.equals(bookedDate) && now.isBefore(expiryTime))) {

	        String lotName = booking.getLotName();
	        int spaceID = booking.getSpaceID();
	        String date = booking.getDate();

	        ParkingLot lot = ManagerAccount.getLotByName(lotName);
	        ParkingSpace space = (lot != null) ? lot.getSpaceById(spaceID) : null;

	        if (space == null) {
	            System.out.println("\n[-] Parking space not found.");
	            return false;
	        }

	        // 2. Mark all current bookings into their time slots to ensure accurate availability
	        List<Booking> allBookings = Database.getInstance().getAllBookings();
	        for (Booking b : allBookings) {
	            if (!b.isCancelled() && !b.isCheckedOut()) {
	                ParkingLot bookedLot = ManagerAccount.getLotByName(b.getLotName());
	                ParkingSpace bookedSpace = (bookedLot != null) ? bookedLot.getSpaceById(b.getSpaceID()) : null;

	                if (bookedSpace != null) {
	                    List<String> timesToBlock = new ArrayList<>();
	                    for (int i = 0; i < b.getDur(); i++) {
	                        timesToBlock.add((b.getStartHour() + i) + ":00");
	                    }
	                    bookedSpace.bookSlots(b.getDate(), timesToBlock);
	                }
	            }
	        }

	        // 3. Validate extension
	        if (booking.canExtend(extraHours, date, space)) {
	            // 4. Perform extension and update CSV
	            database.extendABooking(booking, extraHours, date, space);
	            return true;
	        } else {
	            System.out.println("\n[-] Cannot extend: some time slots are unavailable.");
	        }
	    } else {
	        System.out.println("\n[-] Cannot extend: booking already expired.");
	    }

	    return false;
	}

	
	// Cancel a booking
	public boolean cancelABooking(Booking booking) {
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now();
		LocalDate bookedDate = LocalDate.parse(booking.getDate());
		LocalTime bookingStart = LocalTime.of(booking.getStartHour(), 0);

        if (today.isBefore(bookedDate) || 
        	today.equals(bookedDate) && now.isBefore(bookingStart)) {
            
        	// Mark booking as cancelled
            booking.cancel();

            // Update CSV
            database.cancelABooking(booking);
        	
        	// Release the time slots (un-gray them)
        	ParkingLot lot = ManagerAccount.getLotByName(booking.getLotName());
        	ParkingSpace space = lot.getSpaceById(booking.getSpaceID());
        	List<String> slotsToRelease = new ArrayList<>();
        	for (int i = 0; i < booking.getDur(); i++) {
        	    slotsToRelease.add((booking.getStartHour() + i) + ":00");
        	}
        	space.releaseSlots(booking.getDate(), slotsToRelease);
        	
        	// Refund the deposit
        	PaymentStrategy strategy = null;
        	String method = booking.getPaymentMethod();
        	
        	switch (method.toLowerCase()) {
			case "credit card":
				strategy = new CreditCard();
				break;
			case "debit card":
				strategy = new DebitCard();
				break;
			case "mobile phone":
				strategy = new MobilePayment();
				break;
			default:
				break;
			}
        	strategy.refund(booking.getDeposit());
        	
            return true;
        }
        return false;
	}

}
