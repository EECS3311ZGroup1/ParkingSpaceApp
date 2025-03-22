package yorku.eecs3311.controller;

import yorku.eecs3311.parking.ParkingSpace;
import yorku.eecs3311.parking.TimeSlot;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewBooking extends JPanel {
    
    private List<ParkingSpace> availableSpaces;
    private JComboBox<String> spaceComboBox;
    private JComboBox<String> dateComboBox;
    private JComboBox<Integer> durationComboBox;
    private JPanel timeSlotsPanel;
    private JButton nextButton, backButton;
    private String selectedTimeSlot;

    public ViewBooking(ViewController controller, List<ParkingSpace> availableSpaces) {
    	
        this.availableSpaces = availableSpaces;
    	
        // Central layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Booking Phase 1", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, gbc);

        // Step 1: Choose location
        add(new JLabel("Choose a Parking Location:"), gbc);
        spaceComboBox = new JComboBox<>();
        for (ParkingSpace space : availableSpaces) {
            spaceComboBox.addItem(space.getSpaceLocation());
        }
        spaceComboBox.addActionListener(e -> updateDates());
        add(spaceComboBox, gbc);

        // Step 2: Choose Date
        add(new JLabel("Choose a Date:"), gbc);
        dateComboBox = new JComboBox<>();
        dateComboBox.addActionListener(e -> updateTimeSlots());
        add(dateComboBox, gbc);

        // Step 3: Choose Duration
        add(new JLabel("Choose Duration (Hours):"), gbc);
        durationComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        add(durationComboBox, gbc);

        // Step 4: Choose Start Time
        add(new JLabel("Choose Start Time:"), gbc);
        timeSlotsPanel = new JPanel(new GridLayout(5, 3, 5, 5));
        timeSlotsPanel.setPreferredSize(new Dimension(400, 200));
        add(timeSlotsPanel, gbc);

        // Next button
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> controller.showBookingPlateView(
                (String) spaceComboBox.getSelectedItem(),
                (String) dateComboBox.getSelectedItem(),
                selectedTimeSlot,
                durationComboBox.getSelectedItem().toString()
        ));
        add(nextButton, gbc);

        // Back button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showDashboardView());
        add(backButton, gbc);
        
    }

    private void updateDates() {
        dateComboBox.removeAllItems();
        String selectedSpace = (String) spaceComboBox.getSelectedItem();
        if (selectedSpace == null) return;

        for (ParkingSpace space : availableSpaces) {
            if (space.getSpaceLocation().equals(selectedSpace)) {
                for (String date : space.getAvailableDates()) {
                    dateComboBox.addItem(date);
                }
                dateComboBox.setSelectedIndex(0);
                SwingUtilities.invokeLater(this::updateTimeSlots);
                break;
            }
        }
    }

    private void updateTimeSlots() {
        timeSlotsPanel.removeAll();
        nextButton.setEnabled(false);
        selectedTimeSlot = null;

        String selectedSpace = (String) spaceComboBox.getSelectedItem();
        String selectedDate = (String) dateComboBox.getSelectedItem();

        if (selectedSpace == null || selectedDate == null) return;

        for (ParkingSpace space : availableSpaces) {
            if (space.getSpaceLocation().equals(selectedSpace)) {
                for (TimeSlot slot : space.getSlotsForDate(selectedDate)) {
                    JButton slotButton = new JButton(slot.getTime());
                    slotButton.setEnabled(slot.isAvailable());
                    slotButton.addActionListener(e -> {
                        selectedTimeSlot = slot.getTime();
                        nextButton.setEnabled(true);
                    });
                    timeSlotsPanel.add(slotButton);
                }
                break;
            }
        }

        timeSlotsPanel.revalidate();
        timeSlotsPanel.repaint();
    }
    
}
