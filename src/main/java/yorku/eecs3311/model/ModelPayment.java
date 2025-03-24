package yorku.eecs3311.model;

import java.util.List;

import yorku.eecs3311.Database;
import yorku.eecs3311.booking.Booking;
import yorku.eecs3311.payment.PaymentStrategy;

public class ModelPayment {
	
	private Database database = Database.getInstance();

	// Get un-cancelled un-checkedout bookings by email
	public List<Booking> getUCUCBookingsByEmail(String email) {
		return database.getUCUCBookingsByEmail(email);
	}
	
	// Checkout
	public boolean checkoutBooking(Booking booking, PaymentStrategy strategy, double userRate) {
	    double total = userRate * booking.getDur();
	    double finalCharge = total - booking.getDeposit();
	    
	    booking.setFinalAmountCharged(finalCharge);
	    database.checkoutABooking(booking);
	    
	    return strategy.pay(finalCharge);
	}
	
}
