package com.michiel.banking.service.impl;

import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.exception.ErrorCode;
import com.michiel.banking.graphql.input.BankInput;
import com.michiel.banking.graphql.type.Bank;
import com.michiel.banking.mapper.BankMapper;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.service.BankService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankServiceImpl implements BankService {

  @Autowired
  private BankRepository bankRepository;

  @Autowired
  private BankMapper bankMapper;

  public Bank addBank(BankInput input) {
    List<BankEntity> bankEntities = bankRepository.findByNameIgnoreCase(input.getName());
    BankEntity bankEntity;
    if (bankEntities.size() == 0) {
      bankEntity = this.bankMapper.transform(input);
      bankRepository.save(bankEntity);
    }
    else {
      bankEntity = bankEntities.get(0);
    }
    return this.bankMapper.transform(bankEntity);
  }

  public List<Bank> getBanks() {
    return this.bankMapper.transform(bankRepository.findAll());
  }

  public BankEntity getBankEntityById(long id) throws BankingException {
    Optional<BankEntity> bankEntity = bankRepository.findById(id);
    return bankEntity.orElseThrow(() -> new BankingException(
        ErrorCode.GENERIC_ERROR, "The bank with id=" + id + " does not exist."));
  }

  public Bank getBankById(long id) throws BankingException {
    return this.bankMapper.transform(getBankEntityById(id));
  }
}
