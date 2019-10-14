package com.michiel.banking.mapper;

import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.graphql.input.TransactionInput;
import com.michiel.banking.graphql.type.Transaction;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AccountMapper.class})
public interface TransactionMapper {

  @Mapping(target = "toId", source = "toAccount.id")
  @Mapping(target = "fromId", source = "fromAccount.id")
  Transaction transform(final TransactionEntity entity);

  List<Transaction> transform(final List<TransactionEntity> entity);

  TransactionEntity transform(final TransactionInput input);
}
