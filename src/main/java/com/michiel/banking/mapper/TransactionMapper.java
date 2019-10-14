package com.michiel.banking.mapper;

import com.michiel.banking.entity.TransactionEntity;
import com.michiel.banking.graphql.input.TransactionInput;
import com.michiel.banking.graphql.type.Transaction;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(uses = {AccountMapper.class})
public interface TransactionMapper {

  Transaction transform(final TransactionEntity entity);

  List<Transaction> transform(final List<TransactionEntity> entity);

  TransactionEntity transform(final TransactionInput input);
}
