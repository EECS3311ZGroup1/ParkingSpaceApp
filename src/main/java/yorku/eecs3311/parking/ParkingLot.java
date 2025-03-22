package yorku.eecs3311.parking;

import java.util.*;

public class ParkingLot {
	
	private String lotName;
    private boolean isEnabled;
    private List<ParkingSpace> spaces;

    public ParkingLot(String lotName) {
        this.lotName = lotName;
        this.isEnabled = true;
        this.spaces = new ArrayList<>();

        // Use default 10 spaces per lot for easier test
        for (int i = 1; i <= 10; i++) {
            ParkingSpace space = new ParkingSpace(i, lotName);
            spaces.add(space);
        }
    }

    // IMPORTANT: NEED TO KEEP TRACK OF THIS LIST
    public List<ParkingSpace> getSpaces() {
    	return spaces;
    }
    
    public ParkingSpace getSpaceById(int spaceID) {
    	for (ParkingSpace space : spaces) {
    		if (space.getSpaceID() == spaceID) {
    			return space;
    		}
    	}
    	return null;
    }
    
    // Enable a lot
    public void enable() {
        isEnabled = true;
        for (ParkingSpace space : spaces) {
            space.enable();
        }
    }

    // Disable a lot
    public void disable() {
        isEnabled = false;
        for (ParkingSpace space : spaces) {
            space.disable();
        }
    }
    
    // Enable a space
    public void enableSpace(int spaceID) {
        if (spaceID >= 1 && spaceID <= spaces.size()) {
            spaces.get(spaceID - 1).enable();
        }
    }
    
    // Disable a space
    public void disableSpace(int spaceID) {
        if (spaceID >= 1 && spaceID <= spaces.size()) {
            spaces.get(spaceID - 1).disable();
        }
    }
    
    // Getters
    public String getLotName() { return lotName; }
    public boolean isEnabled() { return isEnabled; }
    
}
