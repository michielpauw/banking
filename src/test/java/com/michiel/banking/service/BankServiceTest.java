package com.michiel.banking.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.rest.input.BankInput;
import com.michiel.banking.rest.type.Bank;
import com.michiel.banking.service.impl.BankServiceImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BankServiceTest {

  @Mock
  private BankRepository bankRepository;


  @Mock
  private BankEntity bankEntity;

  @InjectMocks
  private BankServiceImpl bankServiceImpl;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private BankInput bankInput;

  @Before
  public void prepareTest() {
    bankInput = new BankInput();
    this.bankInput.setName("ING");
  }

  @Test
  public void saveNewBankShouldReturnBank() {
    Mockito.when(this.bankRepository.findByNameIgnoreCase(anyString())).thenReturn(Collections.emptyList());
    Mockito.when(this.bankRepository.save(any(BankEntity.class))).thenReturn(bankEntity);
    Bank bank = bankServiceImpl.saveBank(bankInput);
    assertNotNull(bank);
    assertNotNull(bank.getId());
  }

  @Test
  public void saveExistingBankShouldReturnBank() {
    Mockito.when(this.bankRepository.findByNameIgnoreCase(anyString()))
        .thenReturn(Arrays.asList(bankEntity));
    Bank bank = bankServiceImpl.saveBank(bankInput);
    verify(bankRepository, never()).save(bankEntity);
    assertNotNull(bank);
    assertNotNull(bank.getId());
  }

  @Test
  public void getBanksShouldReturnIterable() {
    Mockito.when(this.bankRepository.findAll()).thenReturn(Arrays.asList(bankEntity));
    Iterable<Bank> banks = bankServiceImpl.getBanks();
    Iterator<Bank> iterator = banks.iterator();
    assertNotNull(banks);
    assertNotNull(iterator.next());
  }

  @Test
  public void getBankByIdShouldReturnBank() {
    Mockito.when(this.bankRepository.findById(anyLong())).thenReturn(Optional.of(bankEntity));
    Bank bank = bankServiceImpl.getBankById(1);
    assertNotNull(bank);
    assertNotNull(bank.getId());
  }

  @Test
  public void getBankByIdShouldThrowException() {
    Mockito.when(this.bankRepository.findById(anyLong())).thenReturn(Optional.empty());
    thrown.expect(NoSuchElementException.class);
    this.bankServiceImpl.getBankById(Mockito.anyLong());
  }
}
