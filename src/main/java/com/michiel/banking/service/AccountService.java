package com.michiel.banking.service;

import com.michiel.banking.entity.AccountType;
import com.michiel.banking.graphql.input.AccountInput;
import com.michiel.banking.graphql.type.Account;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public interface AccountService {
  List<Account> getAccounts();
  Account getAccountById(long id) throws NoSuchElementException;
  Account addAccount(AccountInput input) throws NoSuchElementException;
  List<Long> getCustomerIds(long id);
  Predicate<Account> getAccountPredicate(Long minimum, Long maximum, AccountType type);
  List<Account> getAccounts(Predicate<Account> predicate);
}
