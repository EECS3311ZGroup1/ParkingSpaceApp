package yorku.eecs3311;

import yorku.eecs3311.parking.Car;
import yorku.eecs3311.parking.ParkingLot;
import yorku.eecs3311.parking.ParkingSpace;

public class SensorTest {
	
    public static void main(String[] args) {
    	
        // Get a car
    	Car myCar = new Car("Honda", "Civic", "CJVH578");

        // Get a parking location (lot A, space 1)
        ParkingLot lotA = new ParkingLot("A");
        ParkingSpace space1 = lotA.getSpaceById(1);

        // Simulate car parking
        myCar.park(space1);
        
        // Test car parking
        System.out.println("[TEST] Is car parked? " + space1.isCarParked());

        // Simulate car leaving
        myCar.unpark(space1);
        
        // Test car leaving
        System.out.println("[TEST] Is car parked? " + space1.isCarParked());
        
    }
    
}
