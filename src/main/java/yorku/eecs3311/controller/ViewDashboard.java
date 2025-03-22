package yorku.eecs3311.controller;

import java.awt.*;
import javax.swing.*;

public class ViewDashboard extends JPanel {
	
	private JButton bookParkingButton, extendParkingButton, cancelBookingButton, logoutButton;
	
	public ViewDashboard(ViewController controller) {
     	
		// Central layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, gbc);

        // Book buttons
        bookParkingButton = new JButton("Book a Parking");
        bookParkingButton.addActionListener(e -> controller.showBookingView());
        add(bookParkingButton, gbc);
        
        // Extend button
        extendParkingButton = new JButton("Extend a Parking");
        add(extendParkingButton, gbc);
        
        // Cancel button
        cancelBookingButton = new JButton("Cancel a Booking");
        add(cancelBookingButton, gbc);
        
        // Log out button
        logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> controller.showHeroView());
        add(logoutButton, gbc);
        
    }
	
}
