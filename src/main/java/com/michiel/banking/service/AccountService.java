package com.michiel.banking.service;

import com.michiel.banking.entity.AccountType;
import com.michiel.banking.rest.input.NewAccountInput;
import com.michiel.banking.rest.type.Account;
import java.util.NoSuchElementException;

public interface AccountService {
  Iterable<Account> getAccounts();
  Account getAccountById(long id) throws NoSuchElementException;
  Iterable<Account> getAccounts(Long minimum, Long maximum, AccountType type);
  Account addAccount(NewAccountInput input) throws NoSuchElementException;
}
