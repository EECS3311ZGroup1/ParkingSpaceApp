package yorku.eecs3311;

import javax.swing.SwingUtilities;

import yorku.eecs3311.gui.MainWindow;

public class Main {

	public static void main(String[] args) {
		/*
		 * TEST SuperManager
		 * TEST AutoAccountGeneration
		 * TEST ManagerAccount
		 * TEST Adding manager accounts into the database for later login
		 */
		SuperManager superManager = SuperManager.getInstance();
		
		superManager.generateManagerAccount();
		superManager.generateManagerAccount();
		superManager.generateManagerAccount();
		superManager.showManagerAccounts();
		
		/*
		 * TEST User registration with
		 * -- invalid email
		 * -- invalid pwd
		 * -- invalid ID
		 * -- duplicate
		 */
//		User nam = UserFactory.generateUser("student", "nam@yorku.ca", "aSSWW11@@", "s111");
//		System.out.println(nam);
//		
//		User ping = UserFactory.generateUser("faculty", "ping@yorku.ca", "asasWW#777", "f222");
//		System.out.println(ping);
//		
//		User john = UserFactory.generateUser("visitor", "john@hotmail.com", "ws23**sSS", null);
//		System.out.println(john);
//		
//		User wrongIdUser = UserFactory.generateUser("staff", "ssss@hot.ca", "aasAA@D1((", "t222");
//		System.out.println(wrongIdUser);
		
		// Test Adding User to Database
//		User nam = UserFactory.generateUser("student", "nam13@yorku.ca", "aQQ11@@", "s2222");
//		System.out.println(nam);
		
		//Show GUI
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MainWindow main = new MainWindow();
				main.show();
			}
			
		});
	}

}
