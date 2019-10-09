package com.michiel.banking.repository;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.michiel.banking.entity.BankEntity;
import java.util.Collection;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankRepositoryTest {

  @Autowired
  private BankRepository bankRepository;

  @Before
  public void addInitialData() {
//    final BankEntity bankEntity = new BankEntity();
//    bankEntity.setName("ing");
//    final BankEntity bankEntity1 = this.bankRepository.save(bankEntity);
//    System.out.println(bankEntity1.getName() + " HOLLER!!!: " + bankEntity1.getId());
  }

  @Test
  public void testBankAdd() {
    final BankEntity bankEntity = saveBank("Test");
    assertNotNull(bankEntity.getName());
    assertNull(bankEntity.getAccounts());
  }

  @Test
  public void findBankByNameIgnoreCaseShouldReturnIterableWithBankEntity() {
    final Collection<BankEntity> banks = this.bankRepository.findByNameIgnoreCase("ING");
    assertNotNull(banks);
    assertFalse(CollectionUtils.isEmpty(banks));
    banks.stream().map(BankEntity::getName).forEach(Assert::assertNotNull);

  }

  @Test
  public void findBankByIdShouldReturnBankEntity() {
    final Optional<BankEntity> bank = this.bankRepository.findById((long)1);
    assertTrue(bank.isPresent());
  }

  @Test
  public void findBanksShouldReturnIterableWithBankEntities() {
    final Iterable<BankEntity> banks = this.bankRepository.findAll();
    assertNotNull(banks);
    System.out.println(banks.spliterator().getExactSizeIfKnown());
    assertNotEquals(0, banks.spliterator().getExactSizeIfKnown());
    System.out.println("CHECK!! " + banks.toString());
  }
  @Transactional
  public BankEntity saveBank(final String name) {
    final BankEntity bankEntity = new BankEntity();
    bankEntity.setName(name);
    return this.bankRepository.save(bankEntity);
  }
}
