package com.michiel.banking.mapping;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.graphql.type.Account;
import java.util.ArrayList;
import java.util.List;

public class AccountMap {

  public static Account transform(AccountEntity entity) {
    Account account = new Account();
    account.setBalance(entity.getBalance());
    account.setBank("[NO BANK ENTERED]");
    if (entity.getBank() != null) {
      account.setBank(entity.getBank().getName());
    }
    account.setId(entity.getId());
    account.setType(entity.getType());
    return account;
  }

  public static List<Account> transform(Iterable<AccountEntity> entities) {
    List<Account> accounts = new ArrayList<>();
    if (entities != null) {
      entities.forEach(entity -> {
        Account account = transform(entity);
        accounts.add(account);
      });
    }
    return accounts;
  }
}
