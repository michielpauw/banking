package com.michiel.banking.mapper;

import com.michiel.banking.entity.BankEntity;
import com.michiel.banking.graphql.input.BankInput;
import com.michiel.banking.graphql.type.Bank;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(uses = {AccountMapper.class})
public interface BankMapper {

  Bank transform(final BankEntity entity);

  List<Bank> transform(final List<BankEntity> entity);

  BankEntity transform(final BankInput input);
}
