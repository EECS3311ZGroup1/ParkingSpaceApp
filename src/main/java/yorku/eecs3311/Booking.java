package yorku.eecs3311;

import java.util.Date;

import yorku.eecs3311.observer.Observer;
import yorku.eecs3311.parking.ParkingComponent;
import yorku.eecs3311.publisher.PaymentService;
import yorku.eecs3311.user.User;

public class Booking extends Observer{

	private Date _start;
	private Date _end;
	private ParkingComponent _spot;
	private User _user;
	
	private Booking(PaymentService subject) {
		super(subject);
		// TODO Auto-generated constructor stub
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
		// TODO
	}
	
	public void extend(Date time) {
		//TODO
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void cancel() {
		this.subject.removeObserver(this);
	}

}
