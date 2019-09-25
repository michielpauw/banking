package com.michiel.banking.service;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.rest.AccountTypeFunctionalInterface;
import com.michiel.banking.rest.IntegerFunctionalInterface;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  CustomerRepository customerRepository;

  public AccountEntity saveAccount(AccountEntity accountEntity) {
    return accountRepository.save(accountEntity);
  }

  public Iterable<AccountEntity> saveAccounts(Iterable<AccountEntity> accounts) {
    ArrayList<AccountEntity> savedAccounts = new ArrayList<AccountEntity>();
    accounts.forEach(account -> savedAccounts.add(accountRepository.save(account)));
    return savedAccounts;
  }

  public AccountEntity addCustomerToAccount(
      long accountId,
      long customerId)
      throws NoSuchElementException
  {
    Optional<AccountEntity> accountOptional = accountRepository.findById(accountId);
    Optional<CustomerEntity> customerOptional = customerRepository.findById(customerId);
    if (accountOptional.isPresent() && customerOptional.isPresent()) {
      AccountEntity account = accountOptional.get();
      ArrayList customers = new ArrayList(account.getCustomers());
      customers.add(customerOptional.get());
      account.setCustomers(customers);
      return accountRepository.save(account);
    } else {
      throw new NoSuchElementException();
    }
  }

  public Iterable<AccountEntity> getAccounts() {
    return accountRepository.findAll();
  }

  public AccountEntity getAccountById(long Id) throws NoSuchElementException {
    Optional<AccountEntity> accountEntity = accountRepository.findById(Id);
    if (accountEntity.isPresent()) {
      return accountEntity.get();
    } else {
      throw new NoSuchElementException();
    }
  }

  public Iterable<AccountEntity> filterAccountsByType(AccountType type) {
    Iterable<AccountEntity> accounts = getAccounts();
    return StreamSupport.stream(accounts.spliterator(), false)
            .filter(account -> account.getAccountType() == type)
            .collect(Collectors.toList());
  }

  public Iterable<AccountEntity> getFilteredAccounts(
      IntegerFunctionalInterface integerFilter,
      AccountTypeFunctionalInterface typeFilter
  ) {
    Iterable<AccountEntity> accounts = getAccounts();
    return StreamSupport.stream(accounts.spliterator(), false)
        .filter(account -> integerFilter.integerFilter(account.getBalance()))
        .filter(account -> typeFilter.typeFilter(account.getAccountType()))
        .collect(Collectors.toList());
  }
}
