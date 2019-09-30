package com.michiel.banking.service;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.rest.input.TransactionInput;
import com.michiel.banking.rest.type.Transaction;

public interface TransactionService {
  Transaction handleTransaction(TransactionInput input);
  Transaction transferTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount);
  Transaction depositTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount);
  Iterable<Transaction> getTransactions();
}
