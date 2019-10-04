package com.michiel.banking.graphql;

import com.michiel.banking.graphql.resolver.Mutation;
import com.michiel.banking.graphql.resolver.Query;
import com.michiel.banking.service.AccountService;
import com.michiel.banking.service.BankService;
import com.michiel.banking.service.CustomerService;
import com.michiel.banking.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfiguration {

  @Bean
  public Query query(final AccountService accountService, final BankService bankService,
      final CustomerService customerService, final TransactionService transactionService) {
    return new Query(accountService, bankService, customerService, transactionService);
  }

  @Bean
  public Mutation mutation(final AccountService accountService, final BankService bankService,
      final CustomerService customerService, final TransactionService transactionService) {
    return new Mutation(accountService, bankService, customerService, transactionService);
  }
}
