package com.michiel.banking.mapper;

import com.michiel.banking.entity.CustomerEntity;
import com.michiel.banking.graphql.input.CustomerInput;
import com.michiel.banking.graphql.type.Customer;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(uses = {AccountMapper.class, BankMapper.class})
public interface CustomerMapper {

  Customer transform(final CustomerEntity entity);

  List<Customer> transform(final List<CustomerEntity> entity);

  CustomerEntity transform(final CustomerInput input);
}
