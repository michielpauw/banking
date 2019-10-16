package com.michiel.banking.service.impl;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.exception.ErrorCode;
import com.michiel.banking.graphql.input.CustomerInput;
import com.michiel.banking.graphql.type.Customer;
import com.michiel.banking.mapper.AccountMapper;
import com.michiel.banking.mapper.CustomerMapper;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
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

  @Autowired
  private CustomerMapper customerMapper;

  @Autowired
  private AccountMapper accountMapper;

  public Customer addCustomer(CustomerInput input) {
    return this.customerMapper.transform(customerRepository.save(this.customerMapper.transform(input)));
  }

  public List<Customer> addCustomers(Iterable<CustomerInput> input) {
    List<Customer> customers = new ArrayList<>();
    input.forEach(customer -> customers.add(addCustomer(customer)));
    return customers;
  }

  public List<Customer> getCustomers() {
    return this.customerMapper.transform(customerRepository.findAll());
  }

  public Customer addAccountToCustomer(
      long customerId,
      long accountId)
      throws BankingException
  {
    CustomerEntity customer = getCustomerEntityById(customerId);
    AccountEntity account = accountRepository
        .findById(accountId)
        .orElseThrow(() -> new BankingException(ErrorCode.GENERIC_ERROR, "Account with id=" + accountId + " not found."));
    if (customer.getAccounts().contains(account)) {
      return this.customerMapper.transform(customer);
    }
    customer.getAccounts().add(account);
    return this.customerMapper.transform(customerRepository.save(customer));
  }

  public CustomerEntity getCustomerEntityById(long id) throws BankingException {
    Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
    return customerEntity.orElseThrow(() -> new BankingException(
        ErrorCode.GENERIC_ERROR, "Customer with id=" + id + " not found."));
  }

  public Customer getCustomerById(long id) throws BankingException {
    return this.customerMapper.transform(getCustomerEntityById(id));
  }
}
