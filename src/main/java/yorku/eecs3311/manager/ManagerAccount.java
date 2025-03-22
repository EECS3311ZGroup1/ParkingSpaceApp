package yorku.eecs3311.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import yorku.eecs3311.Subject;
import yorku.eecs3311.Subscriber;
import yorku.eecs3311.Database;
import yorku.eecs3311.parking.ParkingLot;
import yorku.eecs3311.parking.ParkingSpace;

public class ManagerAccount implements Subject {
	
	private String username;
	private String pwd;
	
	private static List<ParkingLot> lots = new ArrayList<>();
	private static List<ParkingSpace> availableSpaces = new ArrayList<>(); // IMPORTANT
	private static List<Subscriber> subscribers = new ArrayList<>();
	
	// Initialize system with parking lots A and B
	static {
        lots.add(new ParkingLot("A"));
        lots.add(new ParkingLot("B"));
        
        System.out.println("\n[*] Preloaded Parking Lots: A, B");
        updateAvailableSpaces();
    }
	
	// Used by Auto Account Generation by the Super Manager direction
	public ManagerAccount(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}
	
	// IMPORTANT
	public static void updateAvailableSpaces() {
		availableSpaces.clear();
		
		for (ParkingLot lot : lots) {
			for (ParkingSpace space : lot.getSpaces()) {
				if (space.isEnabled()) {
					availableSpaces.add(space);
				}
			}
		}
	}
	
	// Add a parking lot
	public void addLot(String lotName) {
	    for (ParkingLot lot : lots) {
	        if (lot.getLotName().equalsIgnoreCase(lotName)) {
	            System.out.println("\n[-] Lot Already Exists: " + lotName);
	            return;
	        }
	    }
	    
	    lots.add(new ParkingLot(lotName));
	    updateAvailableSpaces();
	    notifySubscribers();
	    
	    System.out.println("\n[+] Parking Lot Added: " + lotName);
	}
	
	// Enable a parking lot
	public void enableLot(String lotName) {
		for (ParkingLot lot : lots) {
			if (lot.getLotName().equalsIgnoreCase(lotName)) {
				
				lot.enable();
				updateAvailableSpaces();
				notifySubscribers();
					
				System.out.println("\n[+] Enabled Lot: " + lotName);
				return;
			}
		}
		System.out.println("\n[-] Lot Not Found: " + lotName);
	}
	
	// Disable a parking lot
	public void disableLot(String lotName) {
		for (ParkingLot lot : lots) {
			if (lot.getLotName().equalsIgnoreCase(lotName)) {
				
				lot.disable();
				updateAvailableSpaces();
				notifySubscribers();
					
				System.out.println("\n[+] Disabled Lot: " + lotName);
				return;
			}
		}
		System.out.println("\n[-] Lot Not Found: " + lotName);
	}
	
	// Enable a parking space
	public void enableSpace(String lotName, int spaceID) {
	    for (ParkingLot lot : lots) {
	        if (lot.getLotName().equalsIgnoreCase(lotName)) {
	            
	        	lot.enableSpace(spaceID);
	            updateAvailableSpaces();
	            notifySubscribers();  
	            
	            System.out.println("\n[+] Enabled Space: " + lotName + "-" + spaceID);
	            return;
	        }
	    }
	    System.out.println("\n[-] Lot Not Found: " + lotName);
	}
	
	// Disable a parking space
	public void disableSpace(String lotName, int spaceID) {
	    for (ParkingLot lot : lots) {
	        if (lot.getLotName().equalsIgnoreCase(lotName)) {
	            
	        	lot.disableSpace(spaceID);
	            updateAvailableSpaces();
	            notifySubscribers();
	            
	            System.out.println("\n[+] Disabled Space: " + lotName + "-" + spaceID);
	            return;
	        }
	    }
	    System.out.println("\n[-] Lot Not Found: " + lotName);
	}
	
	// Show available spaces
	public void showAvailableSpaces() {
		System.out.println();
        for (ParkingSpace space : availableSpaces) {
            System.out.println(" - " + space.toString());
        }
        System.out.println("\n[+] Total Available Spaces: " + availableSpaces.size());
    }
	
	// SIMULATE MANAGER UPDATE LOT AND SPACE IN REAL TIME
	public void startManagerCLI() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nManager Menu:");
            System.out.println("---1. Add Lot");
            System.out.println("---2. Enable Lot");
            System.out.println("---3. Disable Lot");
            System.out.println("---4. Enable Space");
            System.out.println("---5. Disable Space");
            System.out.println("---6. View Available Spaces");
            System.out.println("---7. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter lot name: ");
                    String lotName = scanner.nextLine();
                    addLot(lotName);
                    break;
                case 2:
                    System.out.print("Enter lot name: ");
                    String enableLotName = scanner.nextLine();
                    enableLot(enableLotName);
                    break;
                case 3:
                    System.out.print("Enter lot name: ");
                    String disableLotName = scanner.nextLine();
                    disableLot(disableLotName);
                    break;
                case 4:
                    System.out.print("Enter lot name: ");
                    String enableSpaceLot = scanner.nextLine();
                    System.out.print("Enter space ID: ");
                    int enableSpaceID = scanner.nextInt();
                    enableSpace(enableSpaceLot, enableSpaceID);
                    break;
                case 5:
                    System.out.print("Enter lot name: ");
                    String disableSpaceLot = scanner.nextLine();
                    System.out.print("Enter space ID: ");
                    int disableSpaceID = scanner.nextInt();
                    disableSpace(disableSpaceLot, disableSpaceID);
                    break;
                case 6:
                	showAvailableSpaces();
                    break;
                case 7:
                    System.out.println("\n[*] Exiting Manager CLI.");
                    return;
                default:
                    System.out.println("\n[-] Invalid choice. Please try again.");
            }
        }
    }
	
	// Getters
	public String getUsername() { return username; }
	public String getPwd() { return pwd; }
	
	public static List<ParkingLot> getLots() {
		return lots;
	}
	
	public static ParkingLot getLotByName(String lotName) {
		for (ParkingLot lot : lots) {
			if (lot.getLotName().equalsIgnoreCase(lotName)) {
				return lot;
			}
		}
		return null;
	}
	
	public static List<ParkingSpace> getAvailableSpaces() {
		updateAvailableSpaces();
	    return availableSpaces;
	}
	
	// Verify user if they are either Student, Faculty or Staff
	public boolean verifyUser(String id) {
		return Database.getInstance().isValidUserID(id);
	}

	@Override
	public String toString() {
		return "ManagerAccount [username=" + username + ", pwd=" + pwd + "]";
	}

    public static void addSubscriber(Subscriber sub) {
        if (!subscribers.contains(sub)) {
            subscribers.add(sub);
        }
    }

    public static void removeSubscriber(Subscriber sub) {
        subscribers.remove(sub);
    }

    public static void notifySubscribers() {
        for (Subscriber sub : subscribers) {
            sub.update(getAvailableSpaces());
        }
    }
	
}
