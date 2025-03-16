package yorku.eecs3311;

import yorku.eecs3311.observer.Observer;
import yorku.eecs3311.publisher.AvailableParkingPublisher;

public abstract class AvailableParkingObserver extends Observer {
	public AvailableParkingObserver(AvailableParkingPublisher subject) {
		super(subject);
		// TODO Auto-generated constructor stub
	}
}
