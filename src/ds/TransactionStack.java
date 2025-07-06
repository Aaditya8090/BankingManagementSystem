package ds;

import java.util.*;
import model.Transaction;

public class TransactionStack {
    private static TransactionStack instance;
    private HashMap<String, Stack<Transaction>> transactionMap;

    private TransactionStack() {
        transactionMap = new HashMap<>();
    }

    public static TransactionStack getInstance() {
        if (instance == null) {
            instance = new TransactionStack();
        }
        return instance;
    }

    public void pushTransaction(String accNo, Transaction transaction) {
        transactionMap.putIfAbsent(accNo, new Stack<>());
        transactionMap.get(accNo).push(transaction);
    }

    public String getRecentTransactions(String accNo) {
        if (!transactionMap.containsKey(accNo)) {
            return "No transactions found.";
        }

        Stack<Transaction> stack = transactionMap.get(accNo);
        StringBuilder sb = new StringBuilder();

        List<Transaction> recent = new ArrayList<>();
        int count = 0;
        for (int i = stack.size() - 1; i >= 0 && count < 5; i--, count++) {
            recent.add(stack.get(i));
        }

        for (Transaction t : recent) {
            sb.append(t).append("\n");
        }

        return sb.toString();
    }
}
