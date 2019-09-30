package com.michiel.banking.mapping;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.rest.input.AccountInput;
import com.michiel.banking.rest.type.Account;
import java.util.ArrayList;
import java.util.List;

public class AccountMap {

  public static Account transform(AccountEntity entity) {
    Account account = new Account();
    account.setBalance(entity.getBalance());
    account.setBank(entity.getBank().getName());
    account.setId(entity.getId());
    account.setType(entity.getType());
    if (entity.getCustomers() != null) {
      List<Long> customerIds = new ArrayList<>();
      entity.getCustomers().forEach(customer -> customerIds.add(customer.getId()));
      account.setCustomerIds(customerIds);
    }
    return account;
  }

  public static List<Account> transform(Iterable<AccountEntity> entities) {
    List<Account> accounts = new ArrayList<>();
    entities.forEach(entity -> {
      Account account = transform(entity);
      accounts.add(account);
    });
    return accounts;
  }

  public static AccountEntity transform(AccountInput input) {
    AccountEntity entity = new AccountEntity();
    entity.setType(input.getType());
    return entity;
  }
}
