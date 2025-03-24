package yorku.eecs3311.parking;

public class Car {
	private String make;
	private String model;
	private String plateNumber;

	public Car(String make, String model, String plateNumber) {
		this.make = make;
		this.model = model;
		this.plateNumber = plateNumber;
	}
	
	// Simulate parking
	public void park(ParkingSpace space) {
	    space.getSensor().scanCarInfo(this);
	    space.getSensor().sendCarInfo();
	}
	
	// Simulate leaving
	public void unpark(ParkingSpace space) {
	    space.getSensor().clearCarInfo();
	    space.getSensor().sendCarInfo();
	}

	
	// Getters and setters
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	@Override
	public String toString() {
		return "Car [make=" + make + ", model=" + model + ", plateNumber=" + plateNumber + "]";
	}
	
}
