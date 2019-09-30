package com.michiel.banking.service;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.mapping.TransactionMap;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.TransactionRepository;
import com.michiel.banking.rest.input.TransactionInput;
import com.michiel.banking.rest.type.Transaction;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  AccountRepository accountRepository;

  public Transaction handleTransaction(TransactionInput input) {
    if (input.getAmount() < 0) {
      throw new IllegalArgumentException();
    }
    Optional<AccountEntity> accountToOptional = accountRepository.findById(input.getToId());
    if (!(accountToOptional.isPresent())) {
      throw new NoSuchElementException();
    }
    TransactionEntity entity = new TransactionEntity();
    AccountEntity toAccount = accountToOptional.get();
    entity.setAmount(input.getAmount());
    entity.setToAccount(toAccount);
    entity.setType(input.getType());
    if (input.getType() == TransactionType.DEPOSIT) {
      return (depositTransaction(input, entity, toAccount));
    }
    if (input.getFromId() != null) {
      return transferTransaction(input, entity, toAccount);
    }
    throw new IllegalArgumentException();
  }

  public Transaction transferTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount) {
    Optional<AccountEntity> accountFromOptional = accountRepository.findById(input.getFromId());
    if (accountFromOptional.isPresent()) {
      AccountEntity fromAccount = accountFromOptional.get();
      entity.setSuccess(false);
      entity.setFromAccount(fromAccount);
      if (fromAccount.getBalance() > input.getAmount()) {
        toAccount.setBalance(toAccount.getBalance() + input.getAmount());
        fromAccount.setBalance(fromAccount.getBalance() - input.getAmount());
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        entity.setSuccess(true);
      }
      return TransactionMap.transform(transactionRepository.save(entity));
    } else {
      throw new NoSuchElementException();
    }
  }

  public Transaction depositTransaction(TransactionInput input, TransactionEntity entity, AccountEntity toAccount) {
    entity.setSuccess(true);
    toAccount.setBalance(toAccount.getBalance() + input.getAmount());
    accountRepository.save(toAccount);
    return TransactionMap.transform(transactionRepository.save(entity));
  }

  public Iterable<Transaction> getTransactions() {
    return TransactionMap.transform(transactionRepository.findAll());
  }

//
//  public Transaction getTransactionsByFromId(long id) {
//
//    return transactionRepository.
//  }
//
//  public Transaction getTransactionsByToId(long id) {
//
//  }
}
