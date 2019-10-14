package com.michiel.banking.service.impl;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.exception.ErrorCode;
import com.michiel.banking.graphql.input.AccountInput;
import com.michiel.banking.graphql.type.Account;
import com.michiel.banking.mapper.AccountMapper;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.service.AccountService;
import com.michiel.banking.util.StreamUtil;
import java.util.ArrayList;
import java.util.List;
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

  @Autowired
  private AccountMapper accountMapper;

  public List<Account> getAccounts() {
    return this.accountMapper.transform(this.accountRepository.findAll());
  }

  public List<Account> getAccounts(Predicate<Account> predicate) {
    List<Account> accounts = getAccounts();
    return StreamUtil.filter(accounts, predicate);
  }

  public AccountEntity getAccountEntityById(long id) throws BankingException {
    Optional<AccountEntity> accountEntity = accountRepository.findById(id);
    return accountEntity.orElseThrow(() -> new BankingException(
        ErrorCode.GENERIC_ERROR, "The account with id " + id + " does not exist."));
  }

  public Account getAccountById(long id) throws BankingException {
    AccountEntity entity = getAccountEntityById(id);
    return this.accountMapper.transform(entity);
  }

  public Predicate<Account> getAccountPredicate(Long minimum, Long maximum, AccountType type) {
    Predicate<Account> typeFilter = (a) -> type == null || a.getType().equals(type);
    Predicate<Account> amountFilter = (x) -> ((maximum == null && minimum == null)
        || (maximum == null && x.getBalance() >= minimum)
        || (minimum == null && x.getBalance() <= maximum)
        || (minimum != null && maximum != null && x.getBalance() <= maximum && x.getBalance() >= minimum));
    return (x) -> typeFilter.test(x) && amountFilter.test(x);
  }

  private AccountEntity addCustomerToAccount(AccountEntity account, CustomerEntity customer) {
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

  public Account addAccount(AccountInput input) throws BankingException
  {
    CustomerEntity customer = customerRepository
        .findById(input.getCustomerId())
        .orElseThrow(() -> new BankingException(ErrorCode.GENERIC_ERROR, "Customer with id=" + input.getCustomerId() + " not found."));
    BankEntity bank = bankRepository
        .findById(input.getBankId())
        .orElseThrow(() -> new BankingException(ErrorCode.GENERIC_ERROR, "Bank with id=" + input.getBankId() + " not found."));
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setBank(bank);
    accountEntity.setType(input.getType());
    accountEntity = addCustomerToAccount(accountEntity, customer);
    return this.accountMapper.transform(accountRepository.save(accountEntity));
  }

  public List<Long> getCustomerIds(long id) {
    return accountRepository.findByAccountId(id);
  }
}
