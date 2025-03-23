package yorku.eecs3311.controller;

import java.awt.*;
import javax.swing.*;

public class ViewRegistration extends JPanel {
    private JTextField emailField, idField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;
    private JComboBox<String> userTypeDropdown;
    private JLabel idLabel;
    private ViewController controller;

    public ViewRegistration(ViewController controller) {
    	
        this.controller = controller;

        // Center layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("User Registration", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, gbc);

        // Show email
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);
        add(emailLabel, gbc);
        add(emailField, gbc);

        // Show password
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        add(passwordLabel, gbc);
        add(passwordField, gbc);
        
        // Show user type
        JLabel userTypeLabel = new JLabel("User Type:");
        String[] userTypes = {"Student", "Faculty", "Staff", "Visitor"};
        userTypeDropdown = new JComboBox<>(userTypes);
        add(userTypeLabel, gbc);
        add(userTypeDropdown, gbc);
        
        // Show ID
        idLabel = new JLabel("ID:");
        idField = new JTextField(15);
        add(idLabel, gbc);
        add(idField, gbc);

        // Show buttons
        registerButton = new JButton("Register");
        backButton = new JButton("Back");
        add(registerButton, gbc);
        add(backButton, gbc);

        // Action listeners
        userTypeDropdown.addActionListener(e -> toggleIDField());
        registerButton.addActionListener(e -> registerUser());
        backButton.addActionListener(e -> controller.showHeroView());

    }
    
    private void toggleIDField() {
    	String selectedType = (String) userTypeDropdown.getSelectedItem();
    	boolean needsID = !selectedType.equalsIgnoreCase("Visitor");
    	idLabel.setVisible(needsID);
    	idField.setVisible(needsID);
    }
    
    private void registerUser() {
    	String email = emailField.getText();
    	String pwd = new String(passwordField.getPassword());
    	String type = (String) userTypeDropdown.getSelectedItem();
    	String id = idField.getText();
    	
    	// Visitor doesn't need id
    	if (type.equalsIgnoreCase("Visitor")) {
    		id = null;
    	}
    	
    	// Delegate to the controller and get the message
    	String message = controller.registerUser(type, email, pwd, id);
    	
    	if ("SUCCESS".equals(message)) {
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            controller.showHeroView();  // Redirect to Hero View after successful registration
        } else {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
