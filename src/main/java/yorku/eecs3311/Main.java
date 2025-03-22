package yorku.eecs3311;

import yorku.eecs3311.controller.ViewController;
import yorku.eecs3311.manager.ManagerAccount;
import yorku.eecs3311.manager.SuperManager;

import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static final Scanner scanner = new Scanner(System.in);
	
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n[*] Welcome to YorkU Parking Booking System");
            System.out.println("---1. User Mode");
            System.out.println("---2. Manager Mode");
            System.out.println("---3. Super Manager Mode");
            System.out.println("---4. Exit");
            System.out.print("Enter your choice: ");
            
            if (!scanner.hasNextInt()) {
                scanner.next(); 
                System.out.println("\n[-] Invalid input! Please enter a number.");
                continue;
            }
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    startUserMode();
                    break;
                case 2:
                    managerLogin();
                    break;
                case 3:
                	superManagerLogin();
                	break;
                case 4:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("\n[-] Invalid choice. Please try again.");
            }
        }
    }

    // User mode as GUI
    private static void startUserMode() {
        new ViewController();
    }

    // Manager mode as command line
    private static void managerLogin() {
    	SuperManager superManager = SuperManager.getInstance();
    	List<ManagerAccount> managers = superManager.getManagers();
    	
    	System.out.println("\n[*] Please enter your manager credentials.");
        
    	System.out.print("username: ");
        String username = scanner.nextLine();
        
        System.out.print("password: ");
        String password = scanner.nextLine();
        
        // Verify manager login
        for (ManagerAccount manager : managers) {
        	if (manager.getUsername().equals(username) && manager.getPwd().equals(password)) {
        		System.out.println("\n[+] Login successful. Welcome, " + username + "!");
            	manager.startManagerCLI();  // Manager CLI starts
            	return;
        	}
        }
        
        System.out.println("\n[-] Invalid credentials. Try again.");
    }
    
    // Super Manager mode as command line
    private static void superManagerLogin() {
    	SuperManager superManager = SuperManager.getInstance();
    	
    	while (true) {
    		System.out.println("\n[*] Hello Super Manager");
        	System.out.println("---1. Generate New Manager Account");
        	System.out.println("---2. Show Existing Manager Accounts");
        	System.out.println("---3. Exit");
        	System.out.print("Enter your choice: ");
        	
        	if (!scanner.hasNextInt()) {
                scanner.next(); 
                System.out.println("\n[-] Invalid input! Please enter a number.");
                continue;
            }
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    superManager.generateManagerAccount();
                    break;
                case 2:
                    superManager.showManagerAccounts();
                    break;
                case 3:
                	System.out.println("\n[*] See you again, Super Manager...");
                    return;
                default:
                    System.out.println("\n[-] Invalid choice. Please try again.");
            }
        }
    	
    }
    
}