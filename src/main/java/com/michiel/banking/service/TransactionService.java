package com.michiel.banking.service;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.graphql.input.TransactionInput;
import com.michiel.banking.graphql.type.Transaction;
import java.util.function.Predicate;

public interface TransactionService {
  Transaction handleTransaction(TransactionInput input);
  Transaction transferTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount);
  Transaction depositTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount);
  Predicate<Transaction> getTransactionPredicate(Long toId, Long fromId, TransactionType type,
      Long minAmount, Long maxAmount);
  Iterable<Transaction> getTransactions();
  Iterable<Transaction> getTransactions(Predicate<Transaction> predicate);
}
