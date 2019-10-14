package com.michiel.banking.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

  @Bean
  BankMapper bankMapper() { return Mappers.getMapper(BankMapper.class); }

  @Bean
  AccountMapper accountMapper() { return Mappers.getMapper(AccountMapper.class); }

  @Bean
  CustomerMapper customerMapper() { return Mappers.getMapper(CustomerMapper.class); }

  @Bean
  TransactionMapper transactionMapper() { return Mappers.getMapper(TransactionMapper.class); }
}
