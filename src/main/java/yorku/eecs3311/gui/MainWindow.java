package yorku.eecs3311.gui;

import javax.swing.JFrame;

public class MainWindow {
	private JFrame window;
	
	public MainWindow() {
		window = new JFrame();
		window.setTitle("York Parking Space Booking");
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);;
		window.setSize(800, 500);
		window.setLocationRelativeTo(null);
	}
	
	public void show() {
		window.setVisible(true);
	}
}
