package com.michiel.banking.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.repository.BankRepository;
import com.michiel.banking.repository.CustomerRepository;
import com.michiel.banking.rest.input.AccountInput;
import com.michiel.banking.rest.type.Account;
import com.michiel.banking.service.impl.AccountServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private AccountEntity accountEntity;

  @Before
  public void testPreparation() {
    accountEntity = new AccountEntity();
    accountEntity.setId(23423);
    accountEntity.setType(AccountType.CHILD);
    accountEntity.setBank(bankEntity);
    accountEntity.setCustomers(new ArrayList<>(Arrays.asList(customerEntity)));
  }

  @Test
  public void addAccountShouldThrowExceptionIfNoBankWithIdFound() {
    Mockito.when(this.bankRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    thrown.expect(NoSuchElementException.class);
    this.accountService.addAccount(accountInput);
  }

  @Test
  public void addAccountShouldReturnAccountIfAccountAndBankFound() {
    Mockito.when(this.accountRepository.save(Mockito.any(AccountEntity.class))).thenReturn(accountEntity);
    Mockito.when(this.bankRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bankEntity));
    Mockito.when(this.customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customerEntity));
    final Account account = this.accountService.addAccount(accountInput);
    assertNotNull(account);
    assertNotNull(account.getId());
  }

  @Test
  public void getAccountByIdShouldReturnAccountIfFound() {
    Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(accountEntity));
    final Account account = this.accountService.getAccountById(Mockito.anyLong());
    assertNotNull(account.getId());
    assertNotNull(account.getType());
  }

  @Test
  public void getAccountByIdShouldThrowExceptionIfNotFound() {
    Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    thrown.expect(NoSuchElementException.class);
    this.accountService.getAccountById(Mockito.anyLong());
  }

  @Test
  public void getAccountsNoArgsShouldReturnIterable() {
    Mockito.when(this.accountRepository.findAll()).thenReturn(Arrays.asList(accountEntity));
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

  @Test
  public void getFilteredAccountsShouldReturnIterable() {
    AccountEntity account1 = new AccountEntity();
    AccountEntity shouldPass1 = new AccountEntity();
    AccountEntity account3 = new AccountEntity();
    AccountEntity account4 = new AccountEntity();
    AccountEntity shouldPass2 = new AccountEntity();

    account1.setBalance((long)100);
    account1.setType(AccountType.CHILD);
    shouldPass1.setBalance((long)200);
    shouldPass1.setType(AccountType.CHILD);
    account3.setBalance((long)300);
    account3.setType(AccountType.CHILD);
    account4.setBalance((long)200);
    account4.setType(AccountType.INVESTMENT);
    shouldPass2.setBalance((long)200);
    shouldPass2.setType(AccountType.CHILD);

    List<AccountEntity> accounts = new ArrayList<>(Arrays.asList(account1, shouldPass1, account3, account4, shouldPass2));
    Mockito.when(this.accountRepository.findAll()).thenReturn(accounts);
    final Iterable<Account> tripleFilteredAccounts = this.accountService.getAccounts((long)200, (long)250, AccountType.CHILD);
    Iterator<Account> iterator = tripleFilteredAccounts.iterator();
    assertNotNull(tripleFilteredAccounts);
    assertEquals(shouldPass1.getId(), (long)iterator.next().getId());
    assertEquals(shouldPass2.getId(), (long)iterator.next().getId());
    assertFalse(iterator.hasNext());
  }
}
