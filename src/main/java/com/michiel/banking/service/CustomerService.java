package com.michiel.banking.service;

import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.repository.CustomerRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
    return customerRepository.save(customerEntity);
  }

  public Iterable<CustomerEntity> getCustomers() {
    return customerRepository.findAll();
  }

  public CustomerEntity getCustomerById(long Id) throws NoSuchElementException {
    Optional<CustomerEntity> customerEntity = customerRepository.findById(Id);
    if (customerEntity.isPresent()) {
      return customerEntity.get();
    } else {
      throw new NoSuchElementException();
    }
  }
}
