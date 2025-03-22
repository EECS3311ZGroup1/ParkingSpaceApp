package yorku.eecs3311.controller;

import javax.swing.*;
import java.awt.*;

public class ViewBookingConfirm extends JPanel {
	
    private JLabel summaryLabel;
    private JButton confirmButton, backButton;
    private String space, date, time, duration, plate, payment;

    public ViewBookingConfirm(ViewController controller) {
 
    	// Fetch from controller
    	space = controller.getSelectedSpace();
    	date = controller.getSelectedDate();
    	time = controller.getSelectedTime();
    	duration = controller.getSelectedDuration();
    	plate = controller.getSelectedPlateNumber();
    	payment = controller.getSelectedPaymentMethod();
    	
    	// Central layout
    	setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Confirm Your Booking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, gbc);

        // Summary
        summaryLabel = new JLabel(getBookingSummary());
        add(summaryLabel, gbc);

        // Confirm button
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
        	if (controller.bookAParking()) {
        		JOptionPane.showMessageDialog(this, "Booking Confirmed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                controller.showDashboardView();
        	} else {
        		JOptionPane.showMessageDialog(this, "Booking Failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        });
        add(confirmButton, gbc);
        
        // Back button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showBookingPayView(null));
        add(backButton, gbc);
        
    }

    private String getBookingSummary() {
        return "<html><b>Space Location:</b> " + space + "<br>" +
               "<b>Date:</b> " + date + "<br>" +
               "<b>Start Time:</b> " + time + "<br>" +
               "<b>Duration:</b> " + duration + " hour(s)<br>" +
               "<b>Plate Number:</b> " + plate + "<br>" +
               "<b>Payment Method:</b> " + payment + "</html>";
    }
    
}
