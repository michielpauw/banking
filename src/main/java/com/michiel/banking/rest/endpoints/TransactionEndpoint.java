package com.michiel.banking.rest.endpoints;

import com.michiel.banking.rest.input.TransactionInput;
import com.michiel.banking.rest.type.Transaction;
import com.michiel.banking.service.TransactionService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionEndpoint {

  @Autowired
  TransactionService transactionService;

  @PostMapping
  public Transaction saveTransaction(@Valid @RequestBody TransactionInput input){
    return transactionService.handleTransaction(input);
  }

  @GetMapping
  public Iterable<Transaction> getTransactions() {
    return transactionService.getTransactions();
  }
}