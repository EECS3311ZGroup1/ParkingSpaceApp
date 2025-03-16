package yorku.eecs3311;

import java.util.Date;

import yorku.eecs3311.observer.PaymentObserver;
import yorku.eecs3311.parking.ParkingComponent;
import yorku.eecs3311.publisher.PaymentService;
import yorku.eecs3311.user.User;

public class Booking extends PaymentObserver{

	private Date _start;
	private Date _end;
	private ParkingComponent _spot;
	private User _user;
	private Double _amountToPay = 0.0;
	
	private Booking(PaymentService subject) {
		super(subject);
	}
	
	public static Booking book(User user, ParkingComponent spot, Date start, Date end, PaymentService service) {
		Booking booking = new Booking(service);
		booking._user = user;
		booking._start = start;
		booking._end = end;
		booking._spot = spot;
		
		return booking;
	}
	
	public void checkout() {
		_amountToPay = _user.getRate()*getBookingHrDuration();
		//TODO show GUI window
		subject.pay(_amountToPay); //TOOD change amount via GUI
		
	}
	
	private int getBookingHrDuration() {
		double secs = (_end.getTime() - _start.getTime()) / 1000;
		int hours = (int) Math.ceil(secs / 3600.0);
		return hours;
	}
	
	public void extend(Date time) {
		_end = time;
	}

	@Override
	public void update() {
		Double paid = subject.amountExchanged;
		if((int)(paid*100) < _amountToPay.intValue()*100) {
			double balance = _amountToPay-paid;
			subject.pay(balance); //TOOD change amount via GUI
		}
	}
	
	public void cancel() {
		this.subject.removeObserver(this);
	}

}
