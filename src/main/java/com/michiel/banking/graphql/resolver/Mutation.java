package com.michiel.banking.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.michiel.banking.rest.input.BankInput;
import com.michiel.banking.rest.input.CustomerInput;
import com.michiel.banking.rest.input.AccountInput;
import com.michiel.banking.rest.input.TransactionInput;
import com.michiel.banking.rest.type.Account;
import com.michiel.banking.rest.type.Bank;
import com.michiel.banking.rest.type.Customer;
import com.michiel.banking.rest.type.Transaction;
import com.michiel.banking.service.AccountService;
import com.michiel.banking.service.BankService;
import com.michiel.banking.service.CustomerService;
import com.michiel.banking.service.TransactionService;
import java.util.List;

public class Mutation implements GraphQLMutationResolver {

  private final AccountService accountService;
  private final BankService bankService;
  private final CustomerService customerService;
  private final TransactionService transactionService;

  public Mutation(final AccountService accountService, final BankService bankService,
      final CustomerService customerService, final TransactionService transactionService) {
    this.accountService = accountService;
    this.bankService = bankService;
    this.customerService = customerService;
    this.transactionService = transactionService;
  }

  public Customer customer(CustomerInput input) {
    return customerService.addCustomer(input);
  }

  public Iterable<Customer> customers(List<CustomerInput> input) {
    return customerService.addCustomers(input);
  }

  public Account account(AccountInput input) {
    return accountService.addAccount(input);
  }

  public Bank bank(BankInput input) {
    return bankService.addBank(input);
  }

  public Transaction transaction(TransactionInput input) {
    return transactionService.handleTransaction(input);
  }
}
