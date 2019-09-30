package com.michiel.banking.service.impl;

import com.michiel.banking.mapping.AccountMap;
import com.michiel.banking.mapping.CustomerMap;
import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.rest.input.AccountInput;
import com.michiel.banking.rest.input.CustomerInput;
import com.michiel.banking.rest.type.Customer;
import com.michiel.banking.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  BankRepository bankRepository;

  public Customer saveCustomer(CustomerInput input) {
    return CustomerMap.transform(customerRepository.save(CustomerMap.transform(input)));
  }

  public List<Customer> saveCustomers(Iterable<CustomerInput> input) {
    List<Customer> customers = new ArrayList<>();
    input.forEach(customer -> customers.add(saveCustomer(customer)));
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

  public Customer createAccountForCustomerAtBank(
      long customerId,
      long bankId,
      AccountInput input)
      throws NoSuchElementException
  {
    Optional<BankEntity> bankOptional = bankRepository.findById(bankId);
    Optional<CustomerEntity> customerOptional = customerRepository.findById(customerId);
    if (bankOptional.isPresent() && customerOptional.isPresent()) {
      CustomerEntity customer = customerOptional.get();
      AccountEntity accountEntity = accountRepository.save(AccountMap.transform(input));
      accountEntity.setBank(bankOptional.get());
      customer.getAccounts().add(accountEntity);
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
}
