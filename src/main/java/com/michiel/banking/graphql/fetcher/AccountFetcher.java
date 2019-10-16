package com.michiel.banking.graphql.fetcher;

import com.michiel.banking.graphql.type.Account;
import com.michiel.banking.service.AccountService;
import graphql.schema.DataFetchingFieldSelectionSet;
import java.util.List;

public class AccountFetcher {

  private final AccountService accountService;

  public AccountFetcher(AccountService accountService) {
    this.accountService = accountService;
  }

  public List<Account> accountsByCustomerId(Long userId, final DataFetchingFieldSelectionSet selectionSet) {
    final List<Account> accounts = accountService.getCustomerAccounts(userId);
    if (selectionSet.get().getKeys().contains("customerIds")) {
      accounts.forEach(account -> account.setCustomerIds(accountService.getCustomerIds(account.getId())));
    }
    return accounts;
  }

  public Iterable<Account> accountsByBankId(Iterable<Account> accounts, final DataFetchingFieldSelectionSet selectionSet) {
    if (selectionSet.get().getKeys().contains("customerIds")) {
      accounts.forEach(account -> account.setCustomerIds(accountService.getCustomerIds(account.getId())));
    }
    return accounts;
  }
}
