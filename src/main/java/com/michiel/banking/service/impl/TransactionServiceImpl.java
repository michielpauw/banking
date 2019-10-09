package com.michiel.banking.service.impl;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.graphql.input.TransactionInput;
import com.michiel.banking.graphql.type.Transaction;
import com.michiel.banking.mapping.TransactionMap;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.TransactionRepository;
import com.michiel.banking.service.TransactionService;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

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

  public Iterable<Transaction> getTransactions(Long toId, Long fromId, TransactionType type,
      Long minAmount, Long maxAmount) {
    Predicate<Transaction> toFilter = (x) -> true;
    Predicate<Transaction> fromFilter = (x) -> true;
    Predicate<Transaction> amountFilter = (x) -> true;
    Predicate<Transaction> typeFilter = (t) -> true;
    if (toId != null) {
      toFilter = (x) -> x.getToId().equals(toId);
    }
    if (fromId != null) {
      fromFilter = (x) -> x.getFromId().equals(fromId);
    }
    if (maxAmount != null && minAmount != null) {
      amountFilter = (x) -> x.getAmount() <= maxAmount && x.getAmount() >= minAmount;
    } else if (maxAmount != null) {
      amountFilter = (x) -> x.getAmount() <= maxAmount;
    } else if (minAmount != null) {
      amountFilter = (x) -> x.getAmount() >= minAmount;
    }
    if (type != null) {
      typeFilter = (t) -> t.getType().equals(type.toString());
    }
    Iterable<Transaction> transactions = TransactionMap.transform(transactionRepository.findAll());
    return StreamSupport.stream(transactions.spliterator(), false)
        .filter(amountFilter)
        .filter(fromFilter)
        .filter(toFilter)
        .filter(typeFilter)
        .collect(Collectors.toList());
//    return TransactionMap.transform(transactionRepository.findAll());
  }
}
