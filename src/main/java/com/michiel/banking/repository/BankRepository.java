package com.michiel.banking.repository;

import com.michiel.banking.entity.BankEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends CrudRepository<BankEntity, Long> {

}
