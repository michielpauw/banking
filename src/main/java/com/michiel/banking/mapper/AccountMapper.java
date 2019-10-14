package com.michiel.banking.mapper;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.graphql.input.AccountInput;
import com.michiel.banking.graphql.type.Account;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {BankMapper.class, CustomerMapper.class})
public interface AccountMapper {

  @Mapping(target = "bank", source = "bank.name")
  Account transform(final AccountEntity entity);

  List<Account> transform(final List<AccountEntity> entity);

  AccountEntity transform(final AccountInput input);
}
