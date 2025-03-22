package yorku.eecs3311.manager;

import java.util.ArrayList;
import java.util.List;

public class SuperManager {
	
	private static SuperManager instance; // Singleton
	private AutoAccountGeneration autoAccGen;
	private List<ManagerAccount> managers;
	
	private SuperManager() {
		autoAccGen = new AutoAccountGeneration();
		managers = new ArrayList<>();
	}

	public static synchronized SuperManager getInstance() {
		if (instance == null) {
			instance = new SuperManager();
		}
		return instance;
	}
	
	// Generate manager account for management teams
	public void generateManagerAccount() {
		autoAccGen.generateManagerAccount();
	}
	
	// Load accounts from database
	public void loadManagers() {
		managers = autoAccGen.loadManagers();
	}
	
	// Print manager accounts
	public void showManagerAccounts() {
		System.out.println("\n[+] Existing Manager Accounts");
		loadManagers();
		for (ManagerAccount manager : managers) {
			System.out.println("username: " + manager.getUsername() + ", password: " + manager.getPwd());
		}
	}
	
	// Getters
	public List<ManagerAccount> getManagers() {
		loadManagers();
		return managers;
	}
	
}
