package com.michiel.banking.service;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.graphql.input.AccountInput;
import com.michiel.banking.graphql.type.Account;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public interface AccountService {
  List<Account> getAccounts();
  Account getAccountById(long id) throws NoSuchElementException, BankingException;
  Account addAccount(AccountInput input) throws NoSuchElementException, BankingException;
  List<Long> getCustomerIds(long id);
  List<Account> getAccounts(Predicate<Account> predicate);
  AccountEntity getAccountEntityById(long id) throws BankingException;
  Predicate<Account> getAccountPredicate(Long minimum, Long maximum, AccountType type);
}
