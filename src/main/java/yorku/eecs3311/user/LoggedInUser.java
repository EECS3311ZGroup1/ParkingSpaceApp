package yorku.eecs3311.user;

import java.util.ArrayList;
import java.util.List;
import yorku.eecs3311.Subscriber;
import yorku.eecs3311.parking.ParkingSpace;

public class LoggedInUser extends User implements Subscriber {
	private List<ParkingSpace> availableSpaces;
	
	public LoggedInUser(String email, String pwd, String id, double rate) {
		super(email, pwd, id, rate);
		availableSpaces = new ArrayList<>();
	}
	
	public List<ParkingSpace> getAvailableSpaces() {
		return availableSpaces;
	}

	@Override
	public void update(List<ParkingSpace> availableSpaces) {
		this.availableSpaces = availableSpaces;
	}
}
