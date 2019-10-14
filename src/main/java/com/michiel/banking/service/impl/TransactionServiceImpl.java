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
import com.michiel.banking.util.StreamUtil;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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

  public Predicate<Transaction> getTransactionPredicate(Long toId, Long fromId, TransactionType type,
      Long minAmount, Long maxAmount) {
    Predicate<Transaction> toFilter = (x) -> toId == null || x.getToId().equals(toId);
    Predicate<Transaction> fromFilter = (x) -> fromId == null || (x.getFromId() != null && x.getFromId().equals(fromId));
    Predicate<Transaction> typeFilter = (t) -> type == null || t.getType().equals(type);
    Predicate<Transaction> amountFilter = (x) -> ((maxAmount == null && minAmount == null)
        || (maxAmount == null && x.getAmount() >= minAmount)
        || (minAmount == null && x.getAmount() <= maxAmount)
        || (minAmount != null && maxAmount != null && x.getAmount() <= maxAmount && x.getAmount() >= minAmount));
    return (x) -> toFilter.test(x) && fromFilter.test(x) && typeFilter.test(x) && amountFilter.test(x);
  }

  public long getTotalDepositValue() {
    List<Transaction> transactions = getTransactions(this.getTransactionPredicate(null, null, TransactionType.DEPOSIT, null, null));
    return transactions.stream().collect(Collectors.summingLong(Transaction::getAmount));
  }

  public List<Transaction> getTransactions() {
    return TransactionMap.transform(transactionRepository.findAll());
  }

  public List<Transaction> getTransactions(Predicate<Transaction> predicate) {
    List<Transaction> transactions = getTransactions();
    return StreamUtil.filter(transactions, predicate);
  }
}
