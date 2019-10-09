package com.michiel.banking.repository;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.michiel.banking.entity.CustomerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  public void addCustomerShouldReturnCustomerEntity() {
    final CustomerEntity customerEntity = addCustomer("Michiel");
    assertNotNull(customerEntity);
    assertNull(customerEntity.getAccounts());
    assertNotNull(customerEntity.getName());
    assertNotEquals(0, customerEntity.getAge());
  }

  @Transactional
  public CustomerEntity addCustomer(String name) {
    final CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setName(name);
    customerEntity.setAge(31);
    return customerRepository.save(customerEntity);
  }
}
