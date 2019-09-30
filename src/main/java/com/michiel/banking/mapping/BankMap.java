package com.michiel.banking.mapping;

import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.rest.input.BankInput;
import com.michiel.banking.rest.type.Bank;
import java.util.ArrayList;
import java.util.List;

public class BankMap {

  public static Bank transform(BankEntity entity, Bank bank) {
    if (entity.getAccounts() != null) {
      bank.setAccounts(AccountMap.transform(entity.getAccounts()));
    }
    return bank;
  }

  public static Bank transform(BankEntity entity) {
    Bank bank = new Bank();
    bank.setId(entity.getId());
    bank.setName(entity.getName());
    return transform(entity, bank);
  }

  public static List<Bank> transform(Iterable<BankEntity> entities) {
    List<Bank> banks = new ArrayList<>();
    entities.forEach(entity -> {
      Bank bank = transform(entity);
      banks.add(bank);
    });
    return banks;
  }

  public static BankEntity transform(BankInput input) {
    BankEntity entity = new BankEntity();
    entity.setName(input.getName());
    return entity;
  }
}
