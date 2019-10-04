package com.michiel.banking.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.rest.type.Account;
import com.michiel.banking.rest.type.Bank;
import com.michiel.banking.rest.type.Customer;
import com.michiel.banking.rest.type.Transaction;
import com.michiel.banking.service.AccountService;
import com.michiel.banking.service.BankService;
import com.michiel.banking.service.CustomerService;
import com.michiel.banking.service.TransactionService;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;

public class Query implements GraphQLQueryResolver {

  private final AccountService accountService;
  private final BankService bankService;
  private final CustomerService customerService;
  private final TransactionService transactionService;

  public Query(final AccountService accountService, final BankService bankService,
      final CustomerService customerService, final TransactionService transactionService) {
    this.accountService = accountService;
    this.bankService = bankService;
    this.customerService = customerService;
    this.transactionService = transactionService;
  }

  public Customer customer(Long id, final DataFetchingEnvironment dataFetchingEnvironment) {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final Customer customer = customerService.getCustomerById(id);
    if (selectionSet.get().getKeys().contains("accounts")) {
      customer.setAccounts(customerService.getCustomerAccounts(id));
    }
    return customer;
  }

  public Iterable<Customer> customers() {
    return customerService.getCustomers();
  }

  public Account account(Long id, final DataFetchingEnvironment dataFetchingEnvironment) {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final Account account = accountService.getAccountById(id);
    if (selectionSet.get().getKeys().contains("customerIds")) {
      account.setCustomerIds(accountService.getCustomerIds(id));
    }
    return account;
  }

  public Iterable<Account> accounts() {
    return accountService.getAccounts();
  }


  public Iterable<Account> accountsFiltered(Long min, Long max, AccountType type) {
    return accountService.getAccounts(min, max, type);
  }

  public Bank bank(Long id) {
    return bankService.getBankById(id);
  }

  public Iterable<Bank> banks() {
    return bankService.getBanks();
  }

  public Iterable<Transaction> transactions() {
    return transactionService.getTransactions();
  }
}
