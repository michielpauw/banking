package com.michiel.banking.service;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.graphql.input.TransactionInput;
import com.michiel.banking.graphql.type.Transaction;
import java.util.List;
import java.util.function.Predicate;

public interface TransactionService {
  Transaction handleTransaction(TransactionInput input) throws BankingException;
  List<Transaction> getTransactions();
  List<Transaction> getTransactions(Predicate<Transaction> predicate);
  AccountEntity getAccountById(long id) throws BankingException;
  long getTotalDepositValue();
  Predicate<Transaction> getTransactionPredicate(Long toId, Long fromId, TransactionType type,
      Long minAmount, Long maxAmount);
}
