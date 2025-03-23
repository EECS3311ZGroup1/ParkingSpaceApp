package yorku.eecs3311.controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import yorku.eecs3311.booking.Booking;

public class ViewExtend extends JPanel {

    private JComboBox<Integer> bookingComboBox;
    private JLabel dateTimeLabel, bookingDetailLabel;
    private JCheckBox confirmBox;
    private JButton extendButton, backButton;

    public ViewExtend(ViewController controller) {

        // Fetch un-extended (UCUC included) bookings
        List<Booking> bookings = controller.getBookings();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel title = new JLabel("Extend a Booking");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, gbc);

        // Booking ID ComboBox
        add(new JLabel("Select Booking ID:"), gbc);
        bookingComboBox = new JComboBox<>();
        for (Booking b : bookings) {
            bookingComboBox.addItem(b.getBookingID());
        }
        add(bookingComboBox, gbc);

        // Current Date and Time
        dateTimeLabel = new JLabel("Today: " + LocalDate.now() + ", Current Time: " + LocalTime.now().withSecond(0).withNano(0));
        add(dateTimeLabel, gbc);

        // Booking Info
        bookingDetailLabel = new JLabel("Booked Date: -, Start Time: -, Duration: -");
        add(bookingDetailLabel, gbc);

        bookingComboBox.addActionListener(e -> {
            int selectedID = (int) bookingComboBox.getSelectedItem();
            Booking selected = bookings.stream()
                                       .filter(b -> b.getBookingID() == selectedID)
                                       .findFirst().orElse(null);

            if (selected != null) {
                bookingDetailLabel.setText("Booked Date: " + selected.getDate() +
                        ", Start Time: " + selected.getStartHour() + ":00, Duration: " + selected.getDur() + "h");
            }
        });
        
        // Extend hours
        JComboBox<Integer> extraHoursCombo = new JComboBox<>(new Integer[]{1, 2, 3});
        add(extraHoursCombo, gbc);

        // Confirm box
        confirmBox = new JCheckBox("I can only extend before expiry and there are available time slots after.");
        add(confirmBox, gbc);

        // Extend button
        extendButton = new JButton("Extend Booking");
        extendButton.addActionListener(e -> {
            if (!confirmBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please confirm the extension policy.", "Confirmation Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Extend a booking
            int selectedID = (int) bookingComboBox.getSelectedItem();
            int extraHours = (int) extraHoursCombo.getSelectedItem();
            
            if (controller.extendABooking(selectedID, extraHours)) {
            	JOptionPane.showMessageDialog(this, "Booking #" + (int) bookingComboBox.getSelectedItem() + " successfully extended.", "Success", JOptionPane.INFORMATION_MESSAGE);
                controller.showDashboardView();
            } else {
            	JOptionPane.showMessageDialog(this, "You cannot extend the booking.", "Expiry/Not Available", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
        add(extendButton, gbc);

        // Back button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showDashboardView());
        add(backButton, gbc);

        if (bookingComboBox.getItemCount() > 0) {
            bookingComboBox.setSelectedIndex(0);
        }
    }

}
