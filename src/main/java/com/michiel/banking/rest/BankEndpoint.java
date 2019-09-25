package com.michiel.banking.rest;

import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.service.BankService;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
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
  private BankService bankService;

  @PostMapping
  public BankEntity saveBank(@RequestBody BankEntity bankEntity){
    return bankService.saveBank(bankEntity);
  }

  @GetMapping
  public Iterable<BankEntity> getBanks() {
    return bankService.getBanks();
  }

  @GetMapping("/{id}")
  public BankEntity getBankById(@PathVariable(name="id") long bankId, HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      return bankService.getBankById(bankId);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }
}
