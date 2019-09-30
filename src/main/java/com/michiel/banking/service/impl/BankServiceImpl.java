package com.michiel.banking.service.impl;

import com.michiel.banking.mapping.BankMap;
import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.rest.input.BankInput;
import com.michiel.banking.rest.type.Bank;
import com.michiel.banking.service.BankService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

  @Autowired
  private BankRepository bankRepository;

  public Bank saveBank(BankInput input) {
    List<BankEntity> bankEntities = bankRepository.findByNameIgnoreCase(input.getName());
    BankEntity bankEntity;
    if (bankEntities.size() == 0) {
      bankEntity = BankMap.transform(input);
      bankRepository.save(bankEntity);
    }
    else {
      bankEntity = bankEntities.get(0);
    }
    return BankMap.transform(bankEntity);
  }

  public Iterable<Bank> getBanks() {
    return BankMap.transform(bankRepository.findAll());
  }

  public Bank getBankById(long id) throws NoSuchElementException {
    Optional<BankEntity> bankEntity = bankRepository.findById(id);
    if (bankEntity.isPresent()) {
      return BankMap.transform(bankEntity.get());
    } else {
      throw new NoSuchElementException();
    }
  }
}
