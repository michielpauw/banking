package com.michiel.banking.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.graphql.type.Account;
import com.michiel.banking.graphql.type.Bank;
import com.michiel.banking.graphql.type.Customer;
import com.michiel.banking.graphql.type.Transaction;
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

  public Iterable<Customer> customers(final DataFetchingEnvironment dataFetchingEnvironment) {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final Iterable<Customer> customers = customerService.getCustomers();
    if (selectionSet.get().getKeys().contains("accounts")) {
      customers.forEach(customer -> customer.setAccounts(customerService.getCustomerAccounts(customer.getId())));
    }
    return customers;
  }

  public Account account(Long id, final DataFetchingEnvironment dataFetchingEnvironment) {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final Account account = accountService.getAccountById(id);
    if (selectionSet.get().getKeys().contains("customerIds")) {
      account.setCustomerIds(accountService.getCustomerIds(id));
    }
    return account;
  }

  public Iterable<Account> accounts(Long min, Long max, AccountType type, final DataFetchingEnvironment dataFetchingEnvironment) {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final Iterable<Account> accounts = accountService.getAccounts(min, max, type);
    if (selectionSet.get().getKeys().contains("customerIds")) {
      accounts.forEach(account -> account.setCustomerIds(accountService.getCustomerIds(account.getId())));
    }
    return accounts;
  }

  public Bank bank(Long id) {
    return bankService.getBankById(id);
  }

  public Iterable<Bank> banks() {
    return bankService.getBanks();
  }

  public Iterable<Transaction> transactions(Long toId, Long fromId, TransactionType type, Long minAmount, Long maxAmount) {
    return transactionService.getTransactions(toId, fromId, type, minAmount, maxAmount);
  }
}
