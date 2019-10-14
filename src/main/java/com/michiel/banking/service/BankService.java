package com.michiel.banking.service;

import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.graphql.input.BankInput;
import com.michiel.banking.graphql.type.Bank;
import java.util.List;
import java.util.NoSuchElementException;

public interface BankService {

  Bank addBank(BankInput input);
  List<Bank> getBanks();
  Bank getBankById(long id) throws NoSuchElementException, BankingException;
  BankEntity getBankEntityById(long id) throws BankingException;
}
