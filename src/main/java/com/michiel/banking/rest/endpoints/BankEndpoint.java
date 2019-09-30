package com.michiel.banking.rest.endpoints;

import com.michiel.banking.rest.input.BankInput;
import com.michiel.banking.rest.type.Bank;
import com.michiel.banking.service.impl.BankServiceImpl;
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
@RequestMapping("/banks")
public class BankEndpoint {

  @Autowired
  private BankServiceImpl bankService;

  @PostMapping
  public Bank saveBank(@Valid @RequestBody BankInput input) {
    return bankService.saveBank(input);
  }

  @GetMapping
  public Iterable<Bank> getBanks() {
    return bankService.getBanks();
  }

  @RequestMapping("*")
  public String fallbackMethod(HttpServletResponse response){
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    return "Not a valid request.";
  }

  @GetMapping("/{id}")
  public Bank getBankById(@PathVariable(name="id") long bankId, HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return bankService.getBankById(bankId);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }
}
