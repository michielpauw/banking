package com.michiel.banking.mapping;

import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.graphql.input.CustomerInput;
import com.michiel.banking.graphql.type.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerMap {

  public static Customer transform(CustomerEntity entity) {
    Customer customer = new Customer();
    customer.setAge(entity.getAge());
    customer.setId(entity.getId());
    customer.setName(entity.getName());
//    if (entity.getAccounts() != null) {
//      customer.setAccounts(AccountMap.transform(entity.getAccounts()));
//    }
    return customer;
  }

  public static List<Customer> transform(Iterable<CustomerEntity> entities) {
    List<Customer> customers = new ArrayList<>();
    entities.forEach(entity -> {
      Customer customer = transform(entity);
      customers.add(customer);
    });
    return customers;
  }

  public static CustomerEntity transform(CustomerInput input) {
    CustomerEntity entity = new CustomerEntity();
    entity.setName(input.getName());
    if (input.getAge() != null) {
      entity.setAge(input.getAge());
    }
    return entity;
  }
}
