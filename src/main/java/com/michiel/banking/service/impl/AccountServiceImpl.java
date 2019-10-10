package com.michiel.banking.service.impl;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.graphql.input.AccountInput;
import com.michiel.banking.graphql.type.Account;
import com.michiel.banking.mapping.AccountMap;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.service.AccountService;
import com.michiel.banking.util.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
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

  public List<Account> getAccounts() {
    return AccountMap.transform(this.accountRepository.findAll());
  }

  public List<Account> getAccounts(Predicate<Account> predicate) {
    List<Account> accounts = getAccounts();
    return Filter.filter(accounts, predicate);
  }

  public Account getAccountById(long id) throws NoSuchElementException {
    Optional<AccountEntity> accountEntity = accountRepository.findById(id);
    if (accountEntity.isPresent()) {
      return AccountMap.transform(accountEntity.get());
    } else {
      throw new NoSuchElementException();
    }
  }

  public Predicate<Account> getAccountPredicate(Long minimum, Long maximum, AccountType type) {
    Predicate<Account> typeFilter = (a) -> type == null || a.getType().equals(type);
    Predicate<Account> amountFilter = (x) -> ((maximum == null && minimum == null)
        || (maximum == null && x.getBalance() >= minimum)
        || (minimum == null && x.getBalance() <= maximum)
        || (minimum != null && maximum != null && x.getBalance() <= maximum && x.getBalance() >= minimum));
    return (x) -> typeFilter.test(x) && amountFilter.test(x);
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
    return accountRepository.findByAccountId(id);
  }
}
