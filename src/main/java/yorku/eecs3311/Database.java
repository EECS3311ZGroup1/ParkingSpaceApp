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
import yorku.eecs3311.booking.BookingBuilder;
import yorku.eecs3311.manager.ManagerAccount;
import yorku.eecs3311.parking.ParkingSpace;
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
	private List<Booking> bookingCache;
	
	private Database() {
		registeredUsers = new HashMap<>();
		validUserIDs = new HashSet<>();
		bookingCache = new ArrayList<>();
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
	
	// Load bookings into memory for quick lookup
	private void loadBookingsFromFile() {
		bookingCache.clear(); // Always start fresh
		
		try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
			
			reader.readLine(); // Skip header line	
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 12) {
					// Load bookings
					Booking booking = new BookingBuilder()
			        		.setBookingID(Integer.parseInt(data[0]))
			        		.setLotName(data[1])
			        		.setSpaceID(Integer.parseInt(data[2]))
			        		.setDate(data[3])
			        		.setStartHour(Integer.parseInt(data[4]))
			        		.setDur(Integer.parseInt(data[5]))
			        		.setPaymentMethod(data[6])
			        		.setDeposit(Double.parseDouble(data[7]))
			        		.setEmail(data[8])
			        		.isExtended(Boolean.parseBoolean(data[9]))
			        		.isCancelled(Boolean.parseBoolean(data[10]))
			        		.isCheckedOut(Boolean.parseBoolean(data[11]))
			        		.build();
					bookingCache.add(booking);
				}
			}
				
		} catch (Exception e) {
			System.out.println("[-] Failed to load bookings: " + e.getMessage());
		}
	}
	
	// Load email into memory for quick lookup
	private void loadUsersFromFile() {
		registeredUsers.clear(); // Always start fresh
		
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
		validUserIDs.clear(); // Always start fresh
		
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
			
//			writer.newLine();
			writer.write(
					booking.getBookingID() + "," + booking.getLotName() + "," + 
					booking.getSpaceID() + "," + booking.getDate() + "," +
					booking.getStartHour() + "," + booking.getDur() + "," + 
					booking.getPaymentMethod() + "," + booking.getDeposit() + "," +
					booking.getEmail() + "," + booking.isExtended() + "," +
					booking.isCancelled() + "," + booking.isCheckedOut());
			writer.newLine();
			
			// Load cache
			loadBookingsFromFile();
		
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
	
	// Get all bookings
	public List<Booking> getAllBookings() {
		loadBookingsFromFile();
	    return bookingCache;
	}
	
	// Get un-cancelled un-checkedout bookings by email
	public List<Booking> getUCUCBookingsByEmail(String email) {
		loadBookingsFromFile();
		List<Booking> bookings = new ArrayList<>();
		
		for (Booking b : bookingCache) {
			if (b.getEmail().equalsIgnoreCase(email) &&
				b.isCancelled() == false &&
				b.isCheckedOut() == false) {
				
				bookings.add(b);
			}
		}
		
		return bookings;
	}
	
	// Get un-extended (UCUC included) bookings by email
	public List<Booking> getUEBookingsByEmail(String email) {
		loadBookingsFromFile();
		List<Booking> bookings = new ArrayList<>();
		
		for (Booking b : bookingCache) {
			if (b.getEmail().equalsIgnoreCase(email) &&
				b.isCancelled() == false &&
				b.isCheckedOut() == false &&
				b.isExtended() == false) {
				
				bookings.add(b);
			}
		}
		
		return bookings;
	}
	
	// Update extended booking
	public void extendABooking(Booking booking, int extraHours, String date, ParkingSpace space) {
		// Load all bookings
	    List<Booking> allBookings = getAllBookings();

	    // Modify the matching booking
	    for (Booking b : allBookings) {
	        if (b.getBookingID() == booking.getBookingID() &&
	            b.getEmail().equalsIgnoreCase(booking.getEmail())) {
	            
	        	boolean success = b.extend(extraHours, date, space);
	        	if (!success) {
	        		return;
	        	}
	            break;
	        }
	    }

	    // Rewrite the CSV
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
	        // Write header
	        writer.write("id,lot,space,date,start,dur,payment,deposit,email,isExtended,isCancelled,isCheckedOut");
	        writer.newLine();

	        // Write all bookings
	        for (Booking b : allBookings) {
	            writer.write(
	                b.getBookingID() + "," + b.getLotName() + "," +
	                b.getSpaceID() + "," + b.getDate() + "," +
	                b.getStartHour() + "," + b.getDur() + "," +
	                b.getPaymentMethod() + "," + b.getDeposit() + "," +
	                b.getEmail() + "," +
	                b.isExtended() + "," + b.isCancelled() + "," +
	                b.isCheckedOut()
	            );
	            writer.newLine();
	        }

	        // Update cache
	        loadBookingsFromFile();

	        System.out.println("\n[+] Booking extended successfully.");

	    } catch (Exception e) {
	        System.out.println("\n[-] Error writing to CSV: " + e.getMessage());
	    }
	}
	
	// Update cancelled booking
	public void cancelABooking(Booking booking) {
	    // Load all bookings
	    List<Booking> allBookings = getAllBookings();

	    // Modify the matching booking
	    for (Booking b : allBookings) {
	        if (b.getBookingID() == booking.getBookingID() &&
	            b.getEmail().equalsIgnoreCase(booking.getEmail())) {
	            
	        	b.cancel(); // Mark as cancelled
	            break;
	        }
	    }

	    // Rewrite the CSV
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
	        // Write header
	        writer.write("id,lot,space,date,start,dur,payment,deposit,email,isExtended,isCancelled,isCheckedOut");
	        writer.newLine();

	        // Write all bookings
	        for (Booking b : allBookings) {
	            writer.write(
	                b.getBookingID() + "," + b.getLotName() + "," +
	                b.getSpaceID() + "," + b.getDate() + "," +
	                b.getStartHour() + "," + b.getDur() + "," +
	                b.getPaymentMethod() + "," + b.getDeposit() + "," +
	                b.getEmail() + "," +
	                b.isExtended() + "," + b.isCancelled() + "," +
	                b.isCheckedOut()
	            );
	            writer.newLine();
	        }
	        
	        // Update cache
	        loadBookingsFromFile();
	        
	        System.out.println("\n[+] Booking cancelled successfully.");
	        
	    } catch (Exception e) {
	        System.out.println("\n[-] Error writing to CSV: " + e.getMessage());
	    }
	}
	
	// Update check out booking
	public void checkoutABooking(Booking booking) {
		// Load all bookings
	    List<Booking> allBookings = getAllBookings();

	    // Modify the matching booking
	    for (Booking b : allBookings) {
	        if (b.getBookingID() == booking.getBookingID() &&
	            b.getEmail().equalsIgnoreCase(booking.getEmail())) {
	            b.checkout(); // Mark as checked out
	            break;
	        }
	    }

	    // Rewrite the CSV
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
	        // Write header
	        writer.write("id,lot,space,date,start,dur,payment,deposit,email,isExtended,isCancelled,isCheckedOut");
	        writer.newLine();

	        // Write all bookings
	        for (Booking b : allBookings) {
	            writer.write(
	                b.getBookingID() + "," + b.getLotName() + "," +
	                b.getSpaceID() + "," + b.getDate() + "," +
	                b.getStartHour() + "," + b.getDur() + "," +
	                b.getPaymentMethod() + "," + b.getDeposit() + "," +
	                b.getEmail() + "," +
	                b.isExtended() + "," + b.isCancelled() + "," +
	                b.isCheckedOut()
	            );
	            writer.newLine();
	        }
	        
	        // Update cache
	        loadBookingsFromFile();
	        
	        System.out.println("\n[+] Booking checked out successfully.");
	        
	    } catch (Exception e) {
	        System.out.println("\n[-] Error writing to CSV: " + e.getMessage());
	    }
	}
	
}
