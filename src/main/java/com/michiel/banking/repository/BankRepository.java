package com.michiel.banking.repository;

import com.michiel.banking.entity.BankEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long> {
  List<BankEntity> findByNameIgnoreCase(String name);
}
