package yorku.eecs3311;

import java.util.List;
import yorku.eecs3311.parking.ParkingSpace;

public interface Subscriber {
	void update(List<ParkingSpace> availableSpaces);
}
