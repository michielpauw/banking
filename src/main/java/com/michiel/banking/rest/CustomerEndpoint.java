package com.michiel.banking.rest;

import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerEndpoint {

  @Autowired
  CustomerService customerService;

  @PostMapping
  public CustomerEntity saveCustomer(@RequestBody CustomerEntity customerEntity){
    return customerService.saveCustomer(customerEntity);
  }
}
