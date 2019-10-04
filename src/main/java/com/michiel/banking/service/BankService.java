package com.michiel.banking.service;

import com.michiel.banking.rest.input.BankInput;
import com.michiel.banking.rest.type.Bank;
import java.util.NoSuchElementException;

public interface BankService {

  public Bank addBank(BankInput input);
  Iterable<Bank> getBanks();
  Bank getBankById(long id) throws NoSuchElementException;
}
