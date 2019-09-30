package com.michiel.banking.service;

import com.michiel.banking.entity.AccountType;
import com.michiel.banking.rest.input.AccountInput;
import com.michiel.banking.rest.input.NewAccountInput;
import com.michiel.banking.rest.type.Account;
import java.util.NoSuchElementException;

public interface AccountService {
  public Iterable<Account> getAccounts();
  public Account getAccountById(long id) throws NoSuchElementException;
  Iterable<Account> getAccounts(Long minimum, Long maximum, AccountType type);
  Account addBankToAccount(long accountId, long bankId) throws NoSuchElementException;
  Account addAccount(NewAccountInput input) throws NoSuchElementException;
  Account createAccountForCustomerAtBank(long customerId, long bankId, AccountInput input) throws NoSuchElementException;
}
