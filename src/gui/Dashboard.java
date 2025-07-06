package gui;

import javax.swing.*;
import ds.ActivityLogList;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Bank Management System - Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Welcome to Bank Dashboard", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(heading, BorderLayout.NORTH);

        // Panel with navigation buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        JButton customerButton = new JButton("Customer Management");
        JButton transactionButton = new JButton("Transactions");
        JButton loanButton = new JButton("Loan Management");
        JButton activityButton = new JButton("Activity Log");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(customerButton);
        buttonPanel.add(transactionButton);
        buttonPanel.add(loanButton);
        buttonPanel.add(activityButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Action Listeners for Navigation
        customerButton.addActionListener(e -> {
            dispose();
            new CustomerPanel();
        });

        transactionButton.addActionListener(e -> {
            dispose();
            new TransactionPanel();
        });

        loanButton.addActionListener(e -> {
            dispose();
            new LoanPanel();
        });

        activityButton.addActionListener(e -> {
            dispose();
            new ActivityLogPanel();
        });

        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully.");
            ActivityLogList.getInstance().addLog("Admin logged out.");
            dispose();
            new LoginScreen();
        });

        setVisible(true);
    }
}
