package model;

public class LoanRequest implements Comparable<LoanRequest> {
    private String loanId;
    private String accountNumber;
    private double amount;
    private int creditScore;
    private String status;

    public LoanRequest(String loanId, String accountNumber, double amount, int creditScore) {
        this.loanId = loanId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.creditScore = creditScore;
        this.status = "Pending"; // Default
    }

    public String getLoanId() {
        return loanId;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(LoanRequest other) {
        // Higher credit score = higher priority
        return Integer.compare(other.creditScore, this.creditScore);
    }

    @Override
    public String toString() {
        return "Loan ID: " + loanId +", Account No: " + accountNumber + ", Amount: ₹" + amount + ", Credit Score: " + creditScore + ", Status: " + status;
    }
}
