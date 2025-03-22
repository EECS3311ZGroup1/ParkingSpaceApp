package yorku.eecs3311.model;

import yorku.eecs3311.Database;
import yorku.eecs3311.controller.ViewController;

public class ModelLogin {
	
	private Database database;
	
	public ModelLogin() { database = Database.getInstance(); }
	
	public boolean validateUser(String email, String pwd, ViewController controller) {
		if (database.isCredentialsMatched(email, pwd)) {
			controller.setLoggedInUser(database.getLoggedInUser(email));
			return true;
		}
		return false;
	}
	
}
