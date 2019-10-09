package com.michiel.banking.repository;

import com.michiel.banking.entity.AccountEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
  @Query("SELECT Cus.id FROM CustomerEntity Cus JOIN Cus.accounts Acc WHERE Acc.id = :id")
  List<Long> findByAccountId(@Param("id") long id);
}
