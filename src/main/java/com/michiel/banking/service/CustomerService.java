package com.michiel.banking.service;

import com.michiel.banking.graphql.input.CustomerInput;
import com.michiel.banking.graphql.type.Account;
import com.michiel.banking.graphql.type.Customer;
import java.util.List;
import java.util.NoSuchElementException;

public interface CustomerService {
  Customer addCustomer(CustomerInput input);
  List<Customer> addCustomers(Iterable<CustomerInput> input);
  List<Customer> getCustomers();
  Customer addAccountToCustomer(long customerId, long accountId) throws NoSuchElementException;
  Customer getCustomerById(long id) throws NoSuchElementException;
  List<Account> getCustomerAccounts(long id);
}
