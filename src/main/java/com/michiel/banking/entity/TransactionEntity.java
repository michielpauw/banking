package com.michiel.banking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Table(name = "TRANSACTION")
@Entity
public class TransactionEntity {
  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private long id;

  @ManyToOne
  @JoinColumn(name = "from_account_id")
  private AccountEntity fromAccount;

  @ManyToOne
  @JoinColumn(name = "to_account_id")
  private AccountEntity toAccount;

  private Long amount;

  private boolean success;

  private TransactionType type;
}
