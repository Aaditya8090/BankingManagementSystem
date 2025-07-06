package util;

import java.util.Random;

public class IDGenerator {
    private static Random random = new Random();

    public static String generateAccountNumber() {
        return "AC" + (100000 + random.nextInt(900000));
    }

    public static String generateLoanId() {
        return "LN" + (1000 + random.nextInt(9000));
    }
}
