package yorku.eecs3311.manager;

import java.util.List;
import java.util.Map;
import java.util.Random;
import yorku.eecs3311.Database;

public class AutoAccountGeneration {
	
	private Database database;
	
	public AutoAccountGeneration() {
		database = Database.getInstance();
	}
	
	// Generate manager account and add to the database
	public void generateManagerAccount() {
		ManagerAccount manager = new ManagerAccount(generateUsername(), generatePwd());
		database.addManagerToDatabase(manager);
		
		System.out.println("\n[+] Manager account generated successfully: " + manager.getUsername());
	}
	
	// Load manager accounts from the database
	public List<ManagerAccount> loadManagers() {
		return database.loadManagers();
	}
	
	// Generate random name
	private String generateUsername() {
		StringBuilder username = new StringBuilder();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		Random rand = new Random();
		
		for (int i=0; i<5; i++) {
			char randChar = alphabet.charAt(rand.nextInt(alphabet.length()));
			username.append(randChar);
		}
		
		return username.toString();
	}
	
	// Generate random password
	private String generatePwd() {
		StringBuilder pwd = new StringBuilder();
		String hex = "0123456789abcdef";
		Random rand = new Random();
		
		for (int i=0; i<5; i++) {
			char randChar = hex.charAt(rand.nextInt(hex.length()));
			pwd.append(randChar);
		}
		
		return pwd.toString();
	}
	
}