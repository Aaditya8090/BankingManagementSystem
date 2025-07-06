package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Customer;
import model.Transaction;
import ds.ActivityLogList;
import ds.CustomerMap;
import ds.TransactionStack;

public class TransactionPanel extends JFrame {
    private JTextField accountField, amountField;
    private JButton depositBtn, withdrawBtn, viewHistoryBtn, backBtn;
    private JTextArea outputArea;

    private CustomerMap customerMap = CustomerMap.getInstance();
    private TransactionStack transactionStack = TransactionStack.getInstance();

    public TransactionPanel() {
        setTitle("Transaction Panel");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        topPanel.add(new JLabel("Account Number:"));
        accountField = new JTextField();
        topPanel.add(accountField);

        topPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        topPanel.add(amountField);

        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");
        topPanel.add(depositBtn);
        topPanel.add(withdrawBtn);

        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        viewHistoryBtn = new JButton("View Last Transactions");
        backBtn = new JButton("Back to Dashboard");
        bottomPanel.add(viewHistoryBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        depositBtn.addActionListener(e -> handleTransaction(true));
        withdrawBtn.addActionListener(e -> handleTransaction(false));
        viewHistoryBtn.addActionListener(e -> viewHistory());
        backBtn.addActionListener(e -> {
            dispose();
            new Dashboard();
        });

        setVisible(true);
    }

    private void handleTransaction(boolean isDeposit) {
        String acc = accountField.getText().trim();
        String amtStr = amountField.getText().trim();

        if (acc.isEmpty() || amtStr.isEmpty()) {
            outputArea.setText("All fields are required.");
            return;
        }

        Customer customer = customerMap.getCustomer(acc);
        if (customer == null) {
            outputArea.setText("Customer not found.");
            return;
        }

        try {
            double amount = Double.parseDouble(amtStr);
            if (amount <= 0) {
                outputArea.setText("Amount must be positive.");
                return;
            }

            boolean success = false;
            if (isDeposit) {
                customer.deposit(amount);
                success = true;
                ActivityLogList.getInstance().addLog("Deposit of ₹" + amount + " to Account: " + acc);
            } else {
                success = customer.withdraw(amount);
                if (!success) {
                    outputArea.setText("Insufficient balance.");
                    return;
                }
                ActivityLogList.getInstance().addLog("Withdrawal of ₹" + amount + " from Account: " + acc);
            }

            String type = isDeposit ? "Deposit" : "Withdraw";
            Transaction t = new Transaction(acc, amount, type);
            transactionStack.pushTransaction(acc, t);

            outputArea.setText(type + " successful.\nNew Balance: ₹" + customer.getBalance());

        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid amount.");
        }
    }

    private void viewHistory() {
        String acc = accountField.getText().trim();
        if (acc.isEmpty()) {
            outputArea.setText("Enter account number.");
            return;
        }

        outputArea.setText("Last Transactions:\n");
        outputArea.append(transactionStack.getRecentTransactions(acc));
    }
}
