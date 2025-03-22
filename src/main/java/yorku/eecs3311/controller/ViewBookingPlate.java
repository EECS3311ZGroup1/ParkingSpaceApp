package yorku.eecs3311.controller;

import javax.swing.*;
import java.awt.*;

public class ViewBookingPlate extends JPanel {
    
    private JTextField plateField;
    private JCheckBox confirmCheckBox;
    private JButton nextButton, backButton;

    public ViewBookingPlate(ViewController controller) {
    	
    	// Central layout
    	setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Booking Phase 2", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, gbc);

        // Step 1: Enter plate number
        add(new JLabel("Enter your plate number:"), gbc);
        plateField = new JTextField(10);
        add(plateField, gbc);

        // Step 2: Confirm your plate
        confirmCheckBox = new JCheckBox("Does your number plate look right?");
        confirmCheckBox.addActionListener(e -> nextButton.setEnabled(confirmCheckBox.isSelected()));
        add(confirmCheckBox, gbc);

        // Next button
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> controller.showBookingPayView(plateField.getText()));
        add(nextButton, gbc);
        
        // Back button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showBookingView());
        add(backButton, gbc);
        
    }
    
}
