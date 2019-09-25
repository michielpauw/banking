package com.michiel.banking.rest;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.service.AccountService;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountEndpoint {

  @Autowired
  AccountService accountService;

  @PostMapping
  public AccountEntity saveAccount(@RequestBody AccountEntity accountEntity){
    return accountService.saveAccount(accountEntity);
  }

  @PostMapping("/add_multiple")
  public Iterable<AccountEntity> saveAccounts(@RequestBody Iterable<AccountEntity> accounts){
    return accountService.saveAccounts(accounts);
  }

  @PostMapping("/{account_id}/add_customer/{customer_id}")
  public AccountEntity addCustomerToAccount(
      @PathVariable(name="account_id") long accountId,
      @PathVariable(name="customer_id") long customerId,
      HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return accountService.addCustomerToAccount(accountId, customerId);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }

  @GetMapping("/{id}")
  public AccountEntity getAccountById(
      @PathVariable(name="id") long id, HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return accountService.getAccountById(id);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }

  @GetMapping
  public Iterable<AccountEntity> getAccounts(
      @RequestParam(value = "balance-min", required = false) Long minimum,
      @RequestParam(value = "balance-max", required = false) Long maximum,
      @RequestParam(value = "type", required = false) AccountType type
  ) {
    IntegerFunctionalInterface integerFilter = (long x) -> true;
    AccountTypeFunctionalInterface typeFilter = (AccountType account) -> true;
    if (type != null) {
      typeFilter = (AccountType account) -> type == account;
    }
    if (minimum != null && maximum != null) {
      integerFilter = (long x) -> x >= minimum && x <= maximum;
    } else if (minimum != null) {
      integerFilter = (long x) -> x >= minimum;
    } else if (maximum != null) {
      integerFilter = (long x) -> x <= maximum;
    }
    return accountService.getFilteredAccounts(integerFilter, typeFilter);
  }

  @RequestMapping("*")
  public String fallbackMethod(HttpServletResponse response){
    response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    return "Not a valid request.";
  }
}
