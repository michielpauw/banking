package com.michiel.banking.entity;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ACCOUNT")
@Data
public class AccountEntity {
  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private long id;

  @ManyToMany(mappedBy = "accounts")
  private ArrayList<CustomerEntity> customers;

  @OneToOne(mappedBy = "account")
  private BankEntity bank;

  private long balance;
  private AccountType accountType;
}
