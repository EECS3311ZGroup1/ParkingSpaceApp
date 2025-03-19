package yorku.eecs3311.gui;

import javax.swing.SwingUtilities;

public class Launcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Commented out to test App.java
//				ParkingSelectionWindow main = new ParkingSelectionWindow();
//				main.show();
				App app = new App();
				app.setVisible(true); // Use this instead of .show()
			}
			
		});
	}

}
