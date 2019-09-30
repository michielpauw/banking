package com.michiel.banking.rest.endpoints;

import com.michiel.banking.entity.AccountType;
import com.michiel.banking.rest.AccountTypeFunctionalInterface;
import com.michiel.banking.rest.IntegerFunctionalInterface;
import com.michiel.banking.rest.input.AccountInput;
import com.michiel.banking.rest.input.NewAccountInput;
import com.michiel.banking.rest.type.Account;
import com.michiel.banking.service.AccountService;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

  @GetMapping("/{id}")
  public Account getAccountById(@PathVariable(name = "id") long id, HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return accountService.getAccountById(id);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }

  @GetMapping
  public Iterable<Account> getAccounts(
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
  public String fallbackMethod(HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    return "Not a valid request.";
  }

  @PostMapping("/new-account")
  public Account newAccount(
      @Valid @RequestBody NewAccountInput input,
      HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return accountService.newAccount(input);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }

  @PostMapping("/{account-id}/change_bank/{bank-id}")
  public Account addBankToAccount(
      @PathVariable(name = "account-id") long accountId,
      @PathVariable(name = "bank-id") long bankId,
      HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return accountService.addBankToAccount(accountId, bankId);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }

  @PostMapping("/create/{customer-id}/{bank-id}")
  public Account createAccountForCustomerAtBank(
      @PathVariable(name="customer-id") long customerId,
      @PathVariable(name="bank-id") long bankId,
      @Valid @RequestBody AccountInput input,
      HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return accountService.createAccountForCustomerAtBank(customerId, bankId, input);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }
}
