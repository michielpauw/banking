package com.michiel.banking.service;

import com.michiel.banking.graphql.input.BankInput;
import com.michiel.banking.graphql.type.Bank;
import java.util.List;
import java.util.NoSuchElementException;

public interface BankService {

  Bank addBank(BankInput input);
  List<Bank> getBanks();
  Bank getBankById(long id) throws NoSuchElementException;
}
