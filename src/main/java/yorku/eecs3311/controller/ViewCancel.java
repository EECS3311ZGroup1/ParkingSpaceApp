package yorku.eecs3311.controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

import yorku.eecs3311.booking.Booking;

public class ViewCancel extends JPanel {

    private JComboBox<Integer> bookingComboBox;
    private JLabel dateTimeLabel, bookingDetailLabel;
    private JCheckBox confirmBox;
    private JButton cancelButton, backButton;

    public ViewCancel(ViewController controller) {

        // Fetch un-cancelled bookings
        List<Booking> bookings = controller.getBookings();

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel title = new JLabel("Cancel a Booking");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, gbc);
        
        // Booking ID combo
        add(new JLabel("Select Booking ID:"), gbc);
        bookingComboBox = new JComboBox<>();
        for (Booking b : bookings) {
            bookingComboBox.addItem(b.getBookingID());
        }
        add(bookingComboBox, gbc);

        // Date and Time
        dateTimeLabel = new JLabel("Today: " + LocalDate.now() + ", " + 
        					   "Current Time: " + LocalTime.now().withSecond(0).withNano(0));
        add(dateTimeLabel, gbc);

        // Booking details
        bookingDetailLabel = new JLabel("Booked Date: -, Start Time: -");
        add(bookingDetailLabel, gbc);

        // Update booking detail when user selects ID
        bookingComboBox.addActionListener(e -> {
            int selectedID = (int) bookingComboBox.getSelectedItem();
            Booking selected = bookings.stream()
                                       .filter(b -> b.getBookingID() == selectedID)
                                       .findFirst().orElse(null);
            if (selected != null) {
                bookingDetailLabel.setText("Booked Date: " + selected.getDate() + ", Start Time: " + selected.getStartHour() + ":00");
            }
        });

        // Confirm
        confirmBox = new JCheckBox("You can only cancel before the booking's start time.");
        add(confirmBox, gbc);

        // Cancel Booking button
        cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> {
            if (!confirmBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please confirm the cancellation policy.", "Confirmation Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Cancel a booking
            if (controller.cancelABooking((int) bookingComboBox.getSelectedItem())) {
            	JOptionPane.showMessageDialog(this, "Booking #" + (int) bookingComboBox.getSelectedItem() + " successfully cancelled. Full deposit refunded.", "Success", JOptionPane.INFORMATION_MESSAGE);
                controller.showDashboardView();
            } else {
            	JOptionPane.showMessageDialog(this, "You cannot cancel after the booking's start time.", "Too Late", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
        });
        add(cancelButton, gbc);

        // Back Button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showDashboardView());
        add(backButton, gbc);

        // Trigger initial selection
        if (bookingComboBox.getItemCount() > 0) {
            bookingComboBox.setSelectedIndex(0);
        }
    }
    
}
