package yorku.eecs3311.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import yorku.eecs3311.Database;
import yorku.eecs3311.booking.Booking;
import yorku.eecs3311.booking.BookingBuilder;
import yorku.eecs3311.manager.ManagerAccount;
import yorku.eecs3311.parking.ParkingLot;
import yorku.eecs3311.parking.ParkingSpace;

public class ModelBooking {
	
	private Database database = Database.getInstance();
	
	public boolean bookAParking(String spaceLocation, String date, String time, String duration, String plate, String payment, double deposit) {
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
	        		.setStartHour(startHour)
	        		.setDur(dur)
	        		.setPaymentMethod(payment)
	        		.setDeposit(deposit)
	        		.build();
	        
	        database.addBookingToDatabase(booking);
	        System.out.println("\n[+] Booking Created: ID #" + bookingID + " | " + booking);
	        
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return true;
	}

}
