package com.michiel.banking.repository;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.entity.CustomerEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void addAccountShouldReturnAccountEntity() {
    AccountEntity accountEntity = addAccount();
    assertNotNull(accountEntity);
    assertNotNull(accountEntity.getBank());
    assertNotNull(accountEntity.getCustomers());
    assertFalse(accountEntity.getCustomers().isEmpty());
  }

  @Test
  public void findByCustomerIdShouldReturnListOfLongs() {
    List<Long> accountEntities = this.accountRepository.findByAccountId((long)8);
    assertNotNull(accountEntities);
    System.out.println(accountEntities);
    assertEquals(3, accountEntities.size());
  }

  @Test
  public void findAccountsShouldReturnIterableWithAccountEntities() {
    final Iterable<AccountEntity> accounts = this.accountRepository.findAll();
    assertNotNull(accounts);
    assertEquals(8, accounts.spliterator().getExactSizeIfKnown());
  }

  @Test
  public void findAccountByIdShouldReturnOptionalOfAccountEntity() {
    final Optional<AccountEntity> account = this.accountRepository.findById((long)1);
    assertTrue(account.isPresent());
  }

  @Test
  public void findAccountByIdShouldReturnEmptyOptional() {
    final Optional<AccountEntity> account = this.accountRepository.findById((long)1000);
    assertFalse(account.isPresent());
  }

  @Transactional
  public AccountEntity addAccount() {
    final AccountEntity accountEntity = new AccountEntity();
    List<CustomerEntity> customers = new ArrayList<>(Arrays.asList(new CustomerEntity()));
    accountEntity.setCustomers(customers);
    accountEntity.setBank(new BankEntity());
    accountEntity.setType(AccountType.CHILD);
    return this.accountRepository.save(accountEntity);
  }
}
