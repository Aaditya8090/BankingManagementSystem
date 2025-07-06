package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Admin;
import ds.ActivityLogList;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public LoginScreen() {
        setTitle("Bank Management System - Admin Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // UI Panel
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(loginButton);

        statusLabel = new JLabel("");
        panel.add(statusLabel);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Admin admin = new Admin(); // hardcoded for demo
        if (admin.authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            ActivityLogList.getInstance().addLog("Admin logged in.");
            dispose(); // Close login screen
            new Dashboard(); // Open Dashboard (we'll create this next)
        } else {
            statusLabel.setText("Invalid username or password");
        }
    }
}
