package com.michiel.banking.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.michiel.banking.entity.AccountType;
import com.michiel.banking.entity.TransactionType;
import com.michiel.banking.exception.BankingException;
import com.michiel.banking.graphql.fetcher.AccountFetcher;
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
import java.util.List;

public class Query implements GraphQLQueryResolver {

  private final AccountService accountService;
  private final BankService bankService;
  private final CustomerService customerService;
  private final TransactionService transactionService;
  private final AccountFetcher accountFetcher;

  public Query(final AccountService accountService, final BankService bankService,
      final CustomerService customerService, final TransactionService transactionService) {
    this.accountService = accountService;
    this.bankService = bankService;
    this.customerService = customerService;
    this.transactionService = transactionService;
    this.accountFetcher = new AccountFetcher(accountService);
  }

  public Customer customer(Long id, final DataFetchingEnvironment dataFetchingEnvironment)
      throws BankingException {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final Customer customer = customerService.getCustomerById(id);
    if (selectionSet.get().getKeys().contains("accounts")) {
      customer.setAccounts(accountFetcher.accountsByCustomerId(customer.getId(), selectionSet.getField("accounts").getSelectionSet()));
    }
    return customer;
  }

  public List<Customer> customers(final DataFetchingEnvironment dataFetchingEnvironment) {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final List<Customer> customers = customerService.getCustomers();
    if (selectionSet.get().getKeys().contains("accounts")) {
      customers.forEach(customer -> customer.setAccounts(accountFetcher.accountsByCustomerId(customer.getId(), selectionSet.getField("accounts").getSelectionSet())));
    }
    return customers;
  }

  public Account account(Long id, final DataFetchingEnvironment dataFetchingEnvironment) throws BankingException {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final Account account = accountService.getAccountById(id);
    if (selectionSet.get().getKeys().contains("customerIds")) {
      account.setCustomerIds(accountService.getCustomerIds(id));
    }
    return account;
  }


  public List<Account> accounts(Long min, Long max, AccountType type, final DataFetchingEnvironment dataFetchingEnvironment) {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    final List<Account> accounts = accountService.getAccounts(accountService.getAccountPredicate(min, max, type));
    if (selectionSet.get().getKeys().contains("customerIds")) {
      accounts.forEach(account -> account.setCustomerIds(accountService.getCustomerIds(account.getId())));
    }
    return accounts;
  }

  public Bank bank(Long id, final DataFetchingEnvironment dataFetchingEnvironment) throws BankingException {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    Bank bank = bankService.getBankById(id);
    if (selectionSet.get().getKeys().contains("accounts")) {
      bank.setAccounts(accountFetcher.accountsByBankId(bank.getAccounts(), selectionSet.getField("accounts").getSelectionSet()));
    }
    return bank;
  }

  public List<Bank> banks(final DataFetchingEnvironment dataFetchingEnvironment) {
    final DataFetchingFieldSelectionSet selectionSet = dataFetchingEnvironment.getSelectionSet();
    List<Bank> banks = bankService.getBanks();
    if (selectionSet.get().getKeys().contains("accounts")) {
      banks.forEach(bank -> bank.setAccounts(accountFetcher.accountsByBankId(bank.getAccounts(), selectionSet.getField("accounts").getSelectionSet())));
    }
    return banks;
  }

  public List<Transaction> transactions(Long toId, Long fromId, TransactionType type, Long minAmount, Long maxAmount) {
    return transactionService.getTransactions(transactionService.getTransactionPredicate(toId, fromId, type, minAmount, maxAmount));
  }

  public Transaction transaction(Long id) throws BankingException {
    return transactionService.getTransactionById(id);
  }

  public Long totalTransactions() {
    return transactionService.getTotalDepositValue();
  }
}
