package yorku.eecs3311.controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import yorku.eecs3311.booking.Booking;
import yorku.eecs3311.payment.CreditCard;
import yorku.eecs3311.payment.DebitCard;
import yorku.eecs3311.payment.MobilePayment;
import yorku.eecs3311.payment.PaymentStrategy;

public class ViewCheckout extends JPanel {
	
	private JComboBox<Integer> bookingComboBox;
    private JLabel methodLabel;
    private JTextField cardField, expiryField, cvvField, phoneField;
    private JPanel cardPanel, phonePanel;
    private JButton checkoutButton, backButton;
    private PaymentStrategy strategy;

	public ViewCheckout(ViewController controller) {
		
		// Fetch un-cancelled un-checkedout bookings
        List<Booking> bookings = controller.getBookings();
		
        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel title = new JLabel("Checkout a Booking");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, gbc);
        
        // Booking ID combo
        add(new JLabel("Select Booking ID:"), gbc);
        bookingComboBox = new JComboBox<>();
        for (Booking b : bookings) {
            bookingComboBox.addItem(b.getBookingID());
        }
        add(bookingComboBox, gbc);
        
        // Payment method display
        methodLabel = new JLabel("Based on our record, you are paying by -");
        add(methodLabel, gbc);
        
        // Card panel (for credit/debit)
        cardPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        cardField = new JTextField(16);
        expiryField = new JTextField(5);
        cvvField = new JTextField(3);
        cardPanel.add(labeledField("Card Number:", cardField));
        cardPanel.add(labeledField("Expiry Date (MMYY):", expiryField));
        cardPanel.add(labeledField("CVV:", cvvField));
        add(cardPanel, gbc);

        // Phone panel (for mobile)
        phonePanel = new JPanel(new GridLayout(1, 1, 5, 5));
        phoneField = new JTextField(10);
        phonePanel.add(labeledField("Phone Number:", phoneField));
        add(phonePanel, gbc);
        
        // Buttons
        checkoutButton = new JButton("Checkout");
        
        /*
         * Validate payment fields
         */
        checkoutButton.addActionListener(e -> {
            int selectedID = (int) bookingComboBox.getSelectedItem();
            Booking selected = bookings.stream()
                                       .filter(b -> b.getBookingID() == selectedID)
                                       .findFirst().orElse(null);

            if (selected == null) return;

            String method = selected.getPaymentMethod();
            boolean valid = false;

            // Validate payment fields
            if (method.equalsIgnoreCase("Mobile Phone")) {
                String phone = phoneField.getText().trim();
                valid = phone.matches("\\d{10}");
                if (!valid) {
                    JOptionPane.showMessageDialog(this, "Enter valid 10-digit phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                String card = cardField.getText().trim();
                String expiry = expiryField.getText().trim();
                String cvv = cvvField.getText().trim();
                valid = card.matches("\\d{12,16}") && expiry.matches("\\d{4}") && cvv.matches("\\d{3}");
                if (!valid) {
                    JOptionPane.showMessageDialog(this, "Enter valid card info.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Payment strategy
            switch (selected.getPaymentMethod().toLowerCase()) {
                case "credit card":
                    strategy = new CreditCard(); break;
                case "debit card":
                    strategy = new DebitCard(); break;
                case "mobile phone":
                    strategy = new MobilePayment(); break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown payment method", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Call controller
            boolean success = controller.checkoutBooking(selected, strategy);
            if (success) {
                double charged = selected.getFinalAmountCharged();
                JOptionPane.showMessageDialog(this,
                    "Payment Successful!\nYou have been charged $" + charged + " via " + selected.getPaymentMethod(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                controller.showDashboardView();
            } else {
                JOptionPane.showMessageDialog(this, "Checkout failed. This booking may have already been completed or cancelled.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(checkoutButton, gbc);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showDashboardView());
        add(backButton, gbc);

        // Booking selection logic
        bookingComboBox.addActionListener(e -> {
            int selectedID = (int) bookingComboBox.getSelectedItem();
            Booking selected = bookings.stream()
                                       .filter(b -> b.getBookingID() == selectedID)
                                       .findFirst().orElse(null);

            if (selected != null) {
                String method = selected.getPaymentMethod();
                methodLabel.setText("Based on our record, you are paying by " + method);

                if (method.equalsIgnoreCase("Mobile Phone")) {
                    cardPanel.setVisible(false);
                    phonePanel.setVisible(true);
                } else {
                    cardPanel.setVisible(true);
                    phonePanel.setVisible(false);
                }
            }
        });
        
        // Set initial view state
        if (bookingComboBox.getItemCount() > 0) {
            bookingComboBox.setSelectedIndex(0); // triggers listener
        }
        
	}
	
	// Helper for labeled input rows
    private JPanel labeledField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(new JLabel(label), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
	
}
