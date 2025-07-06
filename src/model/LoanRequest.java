package model;

public class LoanRequest implements Comparable<LoanRequest> {
    private String loanId;
    private String accountNumber;
    private double amount;
    private int creditScore;

    public LoanRequest(String loanId, String accountNumber, double amount, int creditScore) {
        this.loanId = loanId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.creditScore = creditScore;
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

    @Override
    public int compareTo(LoanRequest other) {
        // Higher credit score = higher priority
        return Integer.compare(other.creditScore, this.creditScore);
    }

    @Override
    public String toString() {
        return "Account: " + accountNumber + ", Amount: ₹" + amount + ", Credit Score: " + creditScore;
    }
}
