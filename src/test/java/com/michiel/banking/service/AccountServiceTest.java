package com.michiel.banking.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.graphql.input.AccountInput;
import com.michiel.banking.graphql.type.Account;
import com.michiel.banking.mapper.AccountMapper;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.service.impl.AccountServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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
public class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private BankRepository bankRepository;

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private AccountServiceImpl accountService;

  @Mock
  private BankEntity bankEntity;

  @Mock
  private CustomerEntity customerEntity;

  @Mock
  private AccountInput accountInput;

  @Mock
  private AccountMapper accountMapper;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private AccountEntity accountEntity;
  private Account account;

  @Before
  public void testPreparation() {
    accountEntity = new AccountEntity();
    accountEntity.setId(23423);
    accountEntity.setType(AccountType.CHILD);
    accountEntity.setBank(bankEntity);
    accountEntity.setCustomers(new ArrayList<>(Arrays.asList(customerEntity)));
    account = new Account();
    account.setId(accountEntity.getId());
    account.setType(accountEntity.getType());
    Mockito.when(this.accountMapper.transform(Mockito.any(AccountEntity.class))).thenReturn(account);
  }

  @Test
  public void addAccountShouldThrowExceptionIfNoBankWithIdFound() throws BankingException {
    thrown.expect(BankingException.class);
    this.accountService.addAccount(accountInput);
  }

  @Test
  public void addAccountShouldReturnAccountIfAccountAndBankFound() throws BankingException {
    Mockito.when(this.accountRepository.save(Mockito.any(AccountEntity.class))).thenReturn(accountEntity);
    Mockito.when(this.bankRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bankEntity));
    Mockito.when(this.customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customerEntity));
    final Account account = this.accountService.addAccount(accountInput);
    assertNotNull(account);
    assertNotNull(account.getId());
  }

  @Test
  public void getAccountByIdShouldReturnAccountIfFound() throws BankingException {
    Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(accountEntity));
    final Account account = this.accountService.getAccountById(Mockito.anyLong());
    assertNotNull(account.getId());
  }

  @Test
  public void getAccountByIdShouldThrowExceptionIfNotFound() throws BankingException {
    Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    thrown.expect(BankingException.class);
    this.accountService.getAccountById(Mockito.anyLong());
  }

  @Test
  public void getAccountsNoArgsShouldReturnIterable() {
    Mockito.when(this.accountRepository.findAll()).thenReturn(Arrays.asList(accountEntity));
    Mockito.when(this.accountMapper.transform(Mockito.anyList())).thenReturn(Arrays.asList(account));
    final Iterable<Account> accounts = this.accountService.getAccounts();
    Iterator<Account> iterator = accounts.iterator();
    assertNotNull(accounts);
    assertEquals(accountEntity.getId(), (long)iterator.next().getId());
    assertFalse(iterator.hasNext());
  }

  @Test
  public void getAccountsNoArgsShouldReturnEmptyIterable() {
    Mockito.when(this.accountRepository.findAll()).thenReturn(Collections.emptyList());
    final Iterable<Account> accounts = this.accountService.getAccounts();
    Iterator<Account> iterator = accounts.iterator();
    assertNotNull(accounts);
    assertFalse(iterator.hasNext());
  }
}
