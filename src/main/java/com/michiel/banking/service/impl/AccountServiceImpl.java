package com.michiel.banking.service.impl;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.mapping.AccountMap;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.rest.input.AccountInput;
import com.michiel.banking.rest.type.Account;
import com.michiel.banking.service.AccountService;
import com.michiel.banking.service.filter.AccountTypeFunctionalInterface;
import com.michiel.banking.service.filter.IntegerFunctionalInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private BankRepository bankRepository;

  @Autowired
  private CustomerRepository customerRepository;

  public Iterable<Account> getAccounts() {
    return AccountMap.transform(accountRepository.findAll());
  }

  public Account getAccountById(long id) throws NoSuchElementException {
    Optional<AccountEntity> accountEntity = accountRepository.findById(id);
    if (accountEntity.isPresent()) {
      return AccountMap.transform(accountEntity.get());
    } else {
      throw new NoSuchElementException();
    }
  }

  public Iterable<Account> getAccounts(Long minimum, Long maximum, AccountType type) {
    IntegerFunctionalInterface integerFilter = (long x) -> true;
    AccountTypeFunctionalInterface typeFilter = (AccountType account) -> true;
    if (type != null) {
      typeFilter = (AccountType account) -> type == account;
    }
    if (minimum != null && maximum != null) {
      integerFilter = (long x) -> x >= minimum && x <= maximum;
    } else if (minimum != null) {
      integerFilter = (long x) -> x >= minimum;
    } else if (maximum != null) {
      integerFilter = (long x) -> x <= maximum;
    }
    return getFilteredAccounts(integerFilter, typeFilter);
  }

  public Iterable<Account> getFilteredAccounts(
      IntegerFunctionalInterface integerFilter,
      AccountTypeFunctionalInterface typeFilter
  ) {
    Iterable<Account> accounts = getAccounts();
    return StreamSupport.stream(accounts.spliterator(), false)
        .filter(account -> integerFilter.integerFilter(account.getBalance()))
        .filter(account -> typeFilter.typeFilter(account.getType()))
        .collect(Collectors.toList());
  }

  public AccountEntity addCustomerToAccount(AccountEntity account, CustomerEntity customer) {
    List<CustomerEntity> customers = account.getCustomers();
    if (customers != null) {
      customers.add(customer);
    }
    else {
      customers = new ArrayList<>();
      customers.add(customer);
    }
    List<AccountEntity> accounts = customer.getAccounts();
    if (accounts != null) {
      accounts.add(account);
    }
    else {
      accounts = new ArrayList<>();
      accounts.add(account);
    }
    accountRepository.save(account);
    return  account;
  }

  public Account addAccount(AccountInput input) throws NoSuchElementException
  {
    Optional<BankEntity> bankOptional = bankRepository.findById(input.getBankId());
    Optional<CustomerEntity> customerOptional = customerRepository.findById(input.getCustomerId());
    if (bankOptional.isPresent() && customerOptional.isPresent()) {
      CustomerEntity customer = customerOptional.get();
      AccountEntity accountEntity = new AccountEntity();
      accountEntity.setBank(bankOptional.get());
      accountEntity.setType(input.getType());
      accountEntity = addCustomerToAccount(accountEntity, customer);
      return AccountMap.transform(accountRepository.save(accountEntity));
    } else {
      throw new NoSuchElementException();
    }
  }

  public List<Long> getCustomerIds(long id) {
    return accountRepository.findByCustomerId(id);
  }

}
