package com.michiel.banking.service;

import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.repository.BankRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

  @Autowired
  private BankRepository bankRepository;

  public BankEntity saveBank(BankEntity bankEntity) {
    return bankRepository.save(bankEntity);
  }

  public Iterable<BankEntity> getBanks() {
    return bankRepository.findAll();
  }

  public BankEntity getBankById(long Id) throws NoSuchElementException {
    Optional<BankEntity> bankEntity = bankRepository.findById(Id);
    if (bankEntity.isPresent()) {
      return bankEntity.get();
    } else {
      throw new NoSuchElementException();
    }
  }
}
