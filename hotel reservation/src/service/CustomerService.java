package service;
import mode1.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService
{
// Store customers with email as the key
    private Map<String, Customer> customers = new HashMap<>();

    // Static reference (Singleton instance)
    private static CustomerService instance;

    // Private constructor so no one can create a direct object
    private CustomerService() {}

    // Static method to get the single instance
    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    // 1. Add a new customer
    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    // 2. Find and return a customer by email
    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    // 3. Return all customers
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
