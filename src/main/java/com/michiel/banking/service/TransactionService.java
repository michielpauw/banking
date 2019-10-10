package com.michiel.banking.service;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.graphql.input.TransactionInput;
import com.michiel.banking.graphql.type.Transaction;
import java.util.List;
import java.util.function.Predicate;

public interface TransactionService {
  Transaction handleTransaction(TransactionInput input);
  Transaction transferTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount);
  Transaction depositTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount);
  Predicate<Transaction> getTransactionPredicate(Long toId, Long fromId, TransactionType type,
      Long minAmount, Long maxAmount);
  List<Transaction> getTransactions();
  List<Transaction> getTransactions(Predicate<Transaction> predicate);
}
