package com.michiel.banking.rest.endpoints;

import com.michiel.banking.entity.AccountType;
import com.michiel.banking.rest.input.NewAccountInput;
import com.michiel.banking.rest.type.Account;
import com.michiel.banking.service.impl.AccountServiceImpl;
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
  AccountServiceImpl accountService;

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
    return accountService.getAccounts(minimum, maximum, type);


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
      return accountService.addAccount(input);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }
}
