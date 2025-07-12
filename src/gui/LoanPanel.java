package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Customer;
import model.LoanRequest;
import ds.ActivityLogList;
import util.IDGenerator;
import ds.CustomerMap;
import ds.LoanPriorityQueue;

public class LoanPanel extends JFrame {
    private JTextField accField, amountField, creditScoreField;
    private JButton requestBtn, viewBtn, approveBtn, rejectBtn, viewStatusBtn, backBtn;
    private JTextArea outputArea;

    private CustomerMap customerMap = CustomerMap.getInstance();
    private LoanPriorityQueue loanQueue = LoanPriorityQueue.getInstance();

    public LoanPanel() {
        setTitle("Loan Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        form.add(new JLabel("Account Number:"));
        accField = new JTextField();
        form.add(accField);

        form.add(new JLabel("Loan Amount:"));
        amountField = new JTextField();
        form.add(amountField);

        form.add(new JLabel("Credit Score (0–900):"));
        creditScoreField = new JTextField();
        form.add(creditScoreField);

        requestBtn = new JButton("Request Loan");
        form.add(requestBtn);

        viewBtn = new JButton("View Pending Loans");
        approveBtn = new JButton("Approve Top Loan");
        rejectBtn = new JButton("Reject Top Loan");
        viewStatusBtn = new JButton("View Loan Status");
        backBtn = new JButton("Back");

        JPanel controlPanel = new JPanel();
        controlPanel.add(viewBtn);
        controlPanel.add(approveBtn);
        controlPanel.add(rejectBtn);
        controlPanel.add(viewStatusBtn);
        controlPanel.add(backBtn);

        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        requestBtn.addActionListener(e -> requestLoan());
        viewBtn.addActionListener(e -> viewLoans());
        approveBtn.addActionListener(e -> approveLoan());
        rejectBtn.addActionListener(e -> rejectLoan());
        viewStatusBtn.addActionListener(e -> viewLoanStatus());
        backBtn.addActionListener(e -> {
            dispose();
            new Dashboard();
        });

        setVisible(true);
    }

    private void requestLoan() {
        String acc = accField.getText().trim();
        String amtStr = amountField.getText().trim();
        String scoreStr = creditScoreField.getText().trim();

        if (acc.isEmpty() || amtStr.isEmpty() || scoreStr.isEmpty()) {
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
            int creditScore = Integer.parseInt(scoreStr);

            if (creditScore < 0 || creditScore > 900) {
                outputArea.setText("Credit score must be between 0–900.");
                return;
            }

            String loanId = IDGenerator.generateLoanId();

            LoanRequest request = new LoanRequest(loanId, acc, amount, creditScore);
            loanQueue.addLoanRequest(request);
            customer.addLoanRequest(request);  
            outputArea.setText("Loan request submitted successfully.");
            ActivityLogList.getInstance().addLog("Loan Request (Loan ID: " + loanId + ") of ₹" + amount + " submitted by Account No: " + acc);
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid amount or credit score.");
        }
    }

    private void viewLoans() {
        outputArea.setText("Pending Loan Requests (Highest Priority First):\n\n");
        outputArea.append(loanQueue.viewAllLoans());
    }

    private void approveLoan() {
        LoanRequest request = loanQueue.pollLoanRequest();
        request.setStatus("Approved");

        Customer customer = customerMap.getCustomer(request.getAccountNumber());
        if (customer != null) {
            for (LoanRequest lr : customer.getLoanHistory()) {
                if (lr.getLoanId().equals(request.getLoanId())) {
                    lr.setStatus("Approved");
                    break;
                }
            }
        }

        if (request != null) {
            outputArea.setText("Loan Approved:\n" + request);
            ActivityLogList.getInstance().addLog("Loan ID: " + request.getLoanId() + " for Account No: " + request.getAccountNumber() + " has been APPROVED by Admin");
        } else {
            outputArea.setText("No pending loan requests.");
        }
    }

    private void rejectLoan() {
        LoanRequest request = loanQueue.pollLoanRequest();
        request.setStatus("Rejected");

        Customer customer = customerMap.getCustomer(request.getAccountNumber());
        if (customer != null) {
            for (LoanRequest lr : customer.getLoanHistory()) {
                if (lr.getLoanId().equals(request.getLoanId())) {
                    lr.setStatus("Rejected");
                    break;
                }
            }
        }

        if (request != null) {
            outputArea.setText("Loan Rejected:\n" + request);
            ActivityLogList.getInstance().addLog("Loan ID: " + request.getLoanId() + " for Account No: " + request.getAccountNumber() + " has been REJECTED by Admin");
        } else {
            outputArea.setText("No pending loan requests.");
        }
    }

    private void viewLoanStatus() {
        String acc = accField.getText().trim();
        if (acc.isEmpty()) {
            outputArea.setText("Please enter an Account Number.");
            return;
        }

        Customer customer = customerMap.getCustomer(acc);
        if (customer == null) {
            outputArea.setText("Customer not found.");
            return;
        }

        if (customer.getLoanHistory().isEmpty()) {
            outputArea.setText("No loan history found for this account.");
        } else {
            outputArea.setText("Loan History for Account No: " + acc + "\n\n");
            for (LoanRequest request : customer.getLoanHistory()) {
                outputArea.append("- " + request.toString() + "\n");
            }
        }
    }
}
