package com.michiel.banking.service;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.graphql.input.TransactionInput;
import com.michiel.banking.graphql.type.Transaction;

public interface TransactionService {
  Transaction handleTransaction(TransactionInput input);
  Transaction transferTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount);
  Transaction depositTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount);
  Iterable<Transaction> getTransactions(Long toId, Long fromId, TransactionType type,
      Long minAmount, Long maxAmount);
}
