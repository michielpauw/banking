package com.michiel.banking.service;

import com.michiel.banking.entity.AccountType;
import com.michiel.banking.graphql.input.AccountInput;
import com.michiel.banking.graphql.type.Account;
import java.util.List;
import java.util.NoSuchElementException;

public interface AccountService {
  Iterable<Account> getAccounts();
  Account getAccountById(long id) throws NoSuchElementException;
  Iterable<Account> getAccounts(Long minimum, Long maximum, AccountType type);
  Account addAccount(AccountInput input) throws NoSuchElementException;
  List<Long> getCustomerIds(long id);
}
