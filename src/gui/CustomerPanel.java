package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Customer;
import ds.ActivityLogList;
import ds.CustomerMap;

public class CustomerPanel extends JFrame {
    private JTextField nameField, accountField, balanceField;
    private JTextArea outputArea;
    private JButton addButton, searchButton, deleteButton, backButton;

    private static final CustomerMap customerMap = CustomerMap.getInstance();

    public CustomerPanel() {
        setTitle("Customer Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        formPanel.add(new JLabel("Account Number:"));
        accountField = new JTextField();
        formPanel.add(accountField);

        formPanel.add(new JLabel("Customer Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Initial Balance:"));
        balanceField = new JTextField();
        formPanel.add(balanceField);

        addButton = new JButton("Add Customer");
        searchButton = new JButton("Search Customer");
        deleteButton = new JButton("Delete Customer");
        backButton = new JButton("Back to Dashboard");

        formPanel.add(addButton);
        formPanel.add(searchButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(e -> addCustomer());
        searchButton.addActionListener(e -> searchCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard();
        });

        setVisible(true);
    }

    private void addCustomer() {
        try {
            // String account = accountField.getText().trim();
            String name = nameField.getText().trim();
            double balance = Double.parseDouble(balanceField.getText().trim());

            if (name.isEmpty()) {
                outputArea.setText("All fields are required!");
                return;
            }

            Customer customer = new Customer(name, balance);
            customerMap.addCustomer(customer);
            outputArea.setText("Customer added successfully.\n"+ "Generated Account Number: " + customer.getAccountNumber());
            ActivityLogList.getInstance().addLog(
                "Customer " + customer.getName() + 
                " created with Account No: " + customer.getAccountNumber());
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid balance amount.");
        }
    }

    private void searchCustomer() {
        String account = accountField.getText().trim();
        Customer customer = customerMap.getCustomer(account);
        if (customer != null) {
            outputArea.setText("Customer Found:\n" + customer);
        } else {
            outputArea.setText("Customer not found.");
        }
    }

    private void deleteCustomer() {
        String account = accountField.getText().trim();
        boolean removed = customerMap.removeCustomer(account);
        if (removed) {
            outputArea.setText("Customer deleted successfully.");
            ActivityLogList.getInstance().addLog("Customer with Account No: " + account + " has been deleted by Admin");
        } else {
            outputArea.setText("Customer not found.");
        }
    }
}
