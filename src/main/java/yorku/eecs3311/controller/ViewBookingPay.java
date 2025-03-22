package yorku.eecs3311.controller;

import javax.swing.*;
import java.awt.*;

public class ViewBookingPay extends JPanel {

	private JComboBox<String> paymentMethodComboBox;
	private JCheckBox confirmDepositCheckbox;
    private JButton nextButton, backButton;
    private double userRate;

    public ViewBookingPay(ViewController controller) {
    	
    	// Get the logged-in user's rate
        userRate = controller.getLoggedInUser().getRate();
    	
    	// Central layout
    	setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Booking Phase 3", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, gbc);

        // Step 1: Select payment method
        add(new JLabel("Select Payment Method:"), gbc);
        paymentMethodComboBox = new JComboBox<>(new String[]{"Select...", "Credit Card", "Debit Card", "Mobile Phone"});
        paymentMethodComboBox.addActionListener(e -> nextButton.setEnabled(!paymentMethodComboBox.getSelectedItem().equals("Select...")));
        add(paymentMethodComboBox, gbc);
        
        // Step 2: Deposit confirmation
        confirmDepositCheckbox = new JCheckBox("I agree to pay a 1-hour deposit of $" + userRate);
        confirmDepositCheckbox.addActionListener(e -> nextButton.setEnabled(confirmDepositCheckbox.isSelected()));
        add(confirmDepositCheckbox, gbc);

        // Next button
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> controller.showBookingConfirmView(paymentMethodComboBox.getSelectedItem().toString()));
        add(nextButton, gbc);
        
        // Back button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showBookingPlateView(null, null, null, null));
        add(backButton, gbc);
        
    }
    
}
