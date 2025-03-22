package yorku.eecs3311;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import yorku.eecs3311.booking.Booking;
import yorku.eecs3311.manager.ManagerAccount;
import yorku.eecs3311.user.LoggedInUser;
import yorku.eecs3311.user.User;

public class Database {
	
	private static Database instance;
	private static final String IDS_FILE = "src/main/resources/ids.csv";
	private static final String USERS_FILE = "src/main/resources/users.csv";
	private static final String MANAGERS_FILE = "src/main/resources/managers.csv";
	private static final String BOOKINGS_FILE = "src/main/resources/bookings.csv";
	private Map<String, List<String>> registeredUsers;
	private Set<String> validUserIDs;
	
	private Database() {
		registeredUsers = new HashMap<>();
		validUserIDs = new HashSet<>();
		loadUsersFromFile();
		loadValidUserIDsFromFile();
	}
	
	// Singleton pattern
	public static synchronized Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	// Load email into memory for quick lookup
	private void loadUsersFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
				
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 4) {
					// Load users
					List<String> pwdIdRate = new ArrayList<>(3);
					pwdIdRate.add(data[1]);
					pwdIdRate.add(data[2]);
					pwdIdRate.add(data[3]);
					registeredUsers.put(data[0].trim(), pwdIdRate);
				}
			}
				
		} catch (Exception e) {
			System.out.println("[-] Failed to load emails: " + e.getMessage());
		}
	}
	
	// Load user IDs along with their type into memory for quick lookup
	private void loadValidUserIDsFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(IDS_FILE))) {
			
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 2) {
					validUserIDs.add(data[1].trim());
				}
			}
			
		} catch (Exception e) {
			System.out.println("[-] Failed reading valid IDs: " + e.getMessage());
		}
	}
	
	// Check if email is already registered (uses cache)
	public boolean isEmailOrIdRegistered(String email, String id) {
		if (registeredUsers.containsKey(email)) {
			return true;
		}
		
		for (List<String> pwdIdRate : registeredUsers.values()) {
			if (pwdIdRate.get(1).equals(id)) {
				return true;
			}
		}
		
		return false;
	}
	
	// Check if credentials matched
	public boolean isCredentialsMatched(String email, String pwd) {
		return registeredUsers.containsKey(email) && registeredUsers.get(email).get(0).equals(pwd);
	}
	
	// Check if user ID exists with the correct type (uses cache)
	public boolean isValidUserID(String id) {
		return validUserIDs.contains(id);
	}
	
	// Add user to the database
	public void addUserToDatabase(User user) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
			
			writer.newLine();
			writer.write(user.getEmail() + "," + user.getPwd() + "," + user.getId() + "," + user.getRate());
			
			// Update cache
			String email = user.getEmail();
			List<String> userData = new ArrayList<>();
	        userData.add(user.getPwd());
	        userData.add(user.getId());
	        userData.add(String.valueOf(user.getRate()));

	        registeredUsers.put(email, userData);
		
		} catch (Exception e) {
			System.out.println("[-] Failed adding user: " + e.getMessage());
		}
	}
	
	// Add manager to the database
	public void addManagerToDatabase(ManagerAccount manager) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(MANAGERS_FILE, true))) {
			
			writer.newLine();
			writer.write(manager.getUsername() + "," + manager.getPwd());
		
		} catch (Exception e) {
			System.out.println("[-] Failed adding manager: " + e.getMessage());
		}
	}
	
	// Add booking to the database
	public void addBookingToDatabase(Booking booking) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE, true))) {
			
			writer.newLine();
			writer.write(booking.getBookingID() + "," + booking.getLotName() + "," + booking.getSpaceID() + "," + booking.getStartHour() + "," + booking.getDur() + "," + booking.getPaymentMethod() + "," + booking.getDeposit());
			
			// Update cache
			
		
		} catch (Exception e) {
			System.out.println("[-] Failed adding booking: " + e.getMessage());
		}
	}
	
	// Retrieve any existing Manager from the file (first one found) to verify user
	public ManagerAccount getAnyManagerAccount() {
	    try (BufferedReader reader = new BufferedReader(new FileReader(MANAGERS_FILE))) {
	        
	    	String line;
	        while ((line = reader.readLine()) != null) {
	            String[] data = line.split(",");
	            if (data.length == 2) {
	                return new ManagerAccount(data[0].trim(), data[1].trim());
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("[-] Failed retrieving a manager account: " + e.getMessage());
	    }
	    
	    return null;
	}
	
	public List<ManagerAccount> loadManagers() {
		List<ManagerAccount> managers = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(MANAGERS_FILE))) {
	        
			String line;
	        while ((line = reader.readLine()) != null) {
	            String[] data = line.split(",");
	            if (data.length == 2) {
	            	String username = data[0];
	            	String pwd = data[1];
	            	managers.add(new ManagerAccount(username, pwd));
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("[-] Failed retrieving a manager account: " + e.getMessage());
	    }
		
		return managers;
	}
	
	// Get the logged in user
	public LoggedInUser getLoggedInUser(String email) {
		List<String> pwdIdRate = new ArrayList<>();
		pwdIdRate = registeredUsers.get(email);
		
		// Build a logged in user
		String pwd = pwdIdRate.get(0);
		String id = pwdIdRate.get(1);
		double rate = Double.parseDouble(pwdIdRate.get(2));
		
		return new LoggedInUser(email, pwd, id, rate);
	}
	
}
