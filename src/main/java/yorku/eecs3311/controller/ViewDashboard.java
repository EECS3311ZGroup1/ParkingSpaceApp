package yorku.eecs3311.controller;

import java.awt.*;
import javax.swing.*;

public class ViewDashboard extends JPanel {
	
	private JButton bookParkingButton, 
					extendBookingButton, 
					cancelBookingButton,
					checkoutButton,
					logoutButton;
	
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
        extendBookingButton = new JButton("Extend a Booking");
        extendBookingButton.addActionListener(e -> controller.showExtendView());
        add(extendBookingButton, gbc);
        
        // Cancel button
        cancelBookingButton = new JButton("Cancel a Booking");
        cancelBookingButton.addActionListener(e -> controller.showCancelView());
        add(cancelBookingButton, gbc);
        
        // Checkout button
        checkoutButton = new JButton("Checkout a Booking");
        checkoutButton.addActionListener(e -> controller.showCheckoutView());
        add(checkoutButton, gbc);
        
        // Log out button
        logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> controller.showHeroView());
        add(logoutButton, gbc);
        
    }
	
}
