package com.michiel.banking.mapping;

import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.rest.type.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionMap {
  public static Transaction transform(TransactionEntity entity) {
    Transaction transaction = new Transaction();
    if (entity.getType() == TransactionType.TRANSFER) {
      transaction.setFromId(entity.getFromAccount().getId());
    }
    transaction.setToId(entity.getToAccount().getId());
    transaction.setId(entity.getId());
    transaction.setAmount(entity.getAmount());
    transaction.setType(entity.getType());
    transaction.setSuccess(entity.isSuccess());
    return transaction;
  }

  public static List<Transaction> transform(Iterable<TransactionEntity> entities) {
    List<Transaction> transactions = new ArrayList<>();
    entities.forEach(entity -> {
      Transaction transaction = transform(entity);
      transactions.add(transaction);
    });
    return transactions;
  }
}
