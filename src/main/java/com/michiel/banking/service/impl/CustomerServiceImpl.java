package com.michiel.banking.service.impl;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.mapping.AccountMap;
import com.michiel.banking.mapping.CustomerMap;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.graphql.input.CustomerInput;
import com.michiel.banking.graphql.type.Account;
import com.michiel.banking.graphql.type.Customer;
import com.michiel.banking.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private BankRepository bankRepository;

  public Customer addCustomer(CustomerInput input) {
    return CustomerMap.transform(customerRepository.save(CustomerMap.transform(input)));
  }

  public List<Customer> addCustomers(Iterable<CustomerInput> input) {
    List<Customer> customers = new ArrayList<>();
    input.forEach(customer -> customers.add(addCustomer(customer)));
    return customers;
  }

  public Iterable<Customer> getCustomers() {
    return CustomerMap.transform(customerRepository.findAll());
  }

  public Customer addAccountToCustomer(
      long customerId,
      long accountId)
      throws NoSuchElementException
  {
    Optional<AccountEntity> accountOptional = accountRepository.findById(accountId);
    Optional<CustomerEntity> customerOptional = customerRepository.findById(customerId);
    if (accountOptional.isPresent() && customerOptional.isPresent()) {
      CustomerEntity customer = customerOptional.get();
      if (customer.getAccounts().contains(accountOptional.get())) {
        return CustomerMap.transform(customer);
      }
      customer.getAccounts().add(accountOptional.get());
      return CustomerMap.transform(customerRepository.save(customer));
    } else {
      throw new NoSuchElementException();
    }
  }

  public Customer getCustomerById(long id) throws NoSuchElementException {
    Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
    if (customerEntity.isPresent()) {
      return CustomerMap.transform(customerEntity.get());
    } else {
      throw new NoSuchElementException();
    }
  }

  public List<Account> getCustomerAccounts(long id) {
    return AccountMap.transform(customerRepository.findByCustomerId(id));
  }
}
