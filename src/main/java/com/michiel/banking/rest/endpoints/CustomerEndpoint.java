package com.michiel.banking.rest.endpoints;

import com.michiel.banking.rest.input.AccountInput;
import com.michiel.banking.rest.input.CustomerInput;
import com.michiel.banking.rest.type.Customer;
import com.michiel.banking.service.CustomerService;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public Customer saveCustomer(@Valid @RequestBody CustomerInput input){
    return customerService.saveCustomer(input);
  }

  @PostMapping("/multiple")
  public Iterable<Customer> saveCustomers(@Valid @RequestBody Iterable<CustomerInput> input) {
    return customerService.saveCustomers(input);
  }

  @PostMapping("/{customer_id}/add_account/{account_id}")
  public Customer addAccountToCustomer(
      @PathVariable(name="customer_id") long customerId,
      @PathVariable(name="account_id") long accountId,
      HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return customerService.addAccountToCustomer(customerId, accountId);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }

  @PostMapping("/{customer_id}/create_account/{bank_id}")
  public Customer createAccountForCustomerAtBank(
      @PathVariable(name="customer_id") long customerId,
      @PathVariable(name="bank_id") long bankId,
      @Valid @RequestBody AccountInput input,
      HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return customerService.createAccountForCustomerAtBank(customerId, bankId, input);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }

  @GetMapping
  public Iterable<Customer> getCustomers() {
    return customerService.getCustomers();
  }

  @RequestMapping("*")
  public String fallbackMethod(HttpServletResponse response){
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    return "Not a valid request.";
  }

  @GetMapping("/{id}")
  public Customer getCustomerById(
      @PathVariable(name="id") long id, HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return customerService.getCustomerById(id);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }
}
