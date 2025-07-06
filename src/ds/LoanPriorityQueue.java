package ds;

import java.util.PriorityQueue;
import model.LoanRequest;

public class LoanPriorityQueue {
    private static LoanPriorityQueue instance;
    private PriorityQueue<LoanRequest> loanQueue;

    private LoanPriorityQueue() {
        loanQueue = new PriorityQueue<>();
    }

    public static LoanPriorityQueue getInstance() {
        if (instance == null) {
            instance = new LoanPriorityQueue();
        }
        return instance;
    }

    public void addLoanRequest(LoanRequest request) {
        loanQueue.offer(request);
    }

    public LoanRequest pollLoanRequest() {
        return loanQueue.poll();
    }

    public String viewAllLoans() {
        if (loanQueue.isEmpty()) return "No pending loans.\n";

        StringBuilder sb = new StringBuilder();
        for (LoanRequest req : loanQueue) {
            sb.append(req).append("\n");
        }
        return sb.toString();
    }
}
