package com.michiel.banking.repository;

import com.michiel.banking.entity.AccountEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
  @Query("SELECT Cus.id FROM CustomerEntity Cus JOIN Cus.accounts Acc WHERE Acc.id = :id")
  List<Long> findByAccountId(@Param("id") long id);

  @Query("SELECT Acc FROM AccountEntity Acc JOIN Acc.customers Cus WHERE Cus.id = :id")
  List<AccountEntity> findByCustomerId(@Param("id") long id);
}
