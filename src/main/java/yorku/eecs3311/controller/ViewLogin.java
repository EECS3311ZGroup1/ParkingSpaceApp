package yorku.eecs3311.controller;

import java.awt.*;
import javax.swing.*;

public class ViewLogin extends JPanel {
	private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton;
    
    public ViewLogin(ViewController controller) {
        
        // Central layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);
        add(emailLabel, gbc);
        add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        add(passwordLabel, gbc);
        add(passwordField, gbc);

        // Login button
        loginButton = new JButton("Login");
        
        loginButton.addActionListener(e -> {
        	if (controller.validateUser(emailField.getText(), new String(passwordField.getPassword()))) {
        		controller.showDashboardView();
        	} else {
        	JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }});
        
        add(loginButton, gbc);
        
        // Back button
        backButton = new JButton("Back");      
        backButton.addActionListener(e -> controller.showHeroView());
        add(backButton, gbc);
        
    }
    
}
