package com.michiel.banking.repository;

import com.michiel.banking.entity.AccountEntity;
import com.michiel.banking.entity.CustomerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
  @Query("SELECT Acc FROM AccountEntity Acc JOIN Acc.customers Cus WHERE Cus.id = :id")
  List<AccountEntity> findByCustomerId(@Param("id") long id);
}
