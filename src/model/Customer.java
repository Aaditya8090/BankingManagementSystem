package model;

import java.util.ArrayList;
import java.util.List;
import model.LoanRequest;
import util.IDGenerator;

public class Customer {
    private String accountNumber;
    private String name;
    private double balance;
    private List<LoanRequest> loanHistory = new ArrayList<>();

    public Customer(String name, double balance) {
        this.accountNumber = IDGenerator.generateAccountNumber();
        this.name = name;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void addLoanRequest(LoanRequest request) {
        loanHistory.add(request);
    }

    public List<LoanRequest> getLoanHistory() {
        return loanHistory;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
               "\nName: " + name +
               "\nBalance: ₹" + balance;
    }
}
