package ds;

import java.util.HashMap;
import model.Customer;

public class CustomerMap {
    private static CustomerMap instance;
    private HashMap<String, Customer> customerMap;

    private CustomerMap() {
        customerMap = new HashMap<>();
    }

    public static CustomerMap getInstance() {
        if (instance == null) {
            instance = new CustomerMap();
        }
        return instance;
    }

    public void addCustomer(Customer customer) {
        customerMap.put(customer.getAccountNumber(), customer);
    }

    public Customer getCustomer(String accountNumber) {
        return customerMap.get(accountNumber);
    }

    public boolean removeCustomer(String accountNumber) {
        return customerMap.remove(accountNumber) != null;
    }

    public boolean exists(String accountNumber) {
        return customerMap.containsKey(accountNumber);
    }
}
