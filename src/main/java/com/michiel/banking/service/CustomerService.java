package com.michiel.banking.service;

import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.graphql.input.CustomerInput;
import com.michiel.banking.graphql.type.Customer;
import java.util.List;

public interface CustomerService {
  Customer addCustomer(CustomerInput input);
  List<Customer> addCustomers(Iterable<CustomerInput> input);
  List<Customer> getCustomers();
  Customer addAccountToCustomer(long customerId, long accountId) throws BankingException;
  Customer getCustomerById(long id) throws BankingException;
  CustomerEntity getCustomerEntityById(long id) throws BankingException;
}
