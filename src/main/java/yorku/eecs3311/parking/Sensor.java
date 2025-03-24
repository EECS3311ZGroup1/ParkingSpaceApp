package yorku.eecs3311.parking;

public class Sensor {
	
	private boolean isCarParked;
	String make, model, plateNumber;
	
	public Sensor() {
		isCarParked = false;
		make = "";
		model = "";
		plateNumber = "";
	}
	
	public void scanCarInfo(Car car) {
		isCarParked = true;
		make = car.getMake();
		model = car.getModel();
		plateNumber = car.getPlateNumber();
	}
	
	public void clearCarInfo() {
		isCarParked = false;
		make = "";
		model = "";
		plateNumber = "";
	}
	
	public void sendCarInfo() {
		System.out.println("\n[*] Sending car info to the system...");
		System.out.println("[+] Car [make=" + make + ", model=" + model + ", plateNumber=" + plateNumber + "]");
		
	}
	
	public boolean isCarParked() {
		return isCarParked;
	}
	
}
