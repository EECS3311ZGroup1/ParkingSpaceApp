package yorku.eecs3311.controller;

import javax.swing.*;
import java.awt.*;

public class ViewMain extends JFrame {
	
	private JPanel mainPanel;
	private CardLayout cardLayout;
	
	public ViewMain() {
		
		// Set up the main frame facing users
		setTitle("YorkU Parking Booking App");
		setSize(900, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// Set up the main panel (container)
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		// Add main panel to the frame
		add(mainPanel);
	}
	
	// Add different views to the main window
	public void addView(String name, JPanel panel) {
		mainPanel.add(panel, name);
	}
	
	// Switch between views
	public void showView(String name) {
		cardLayout.show(mainPanel, name);
		
	}
	
}
