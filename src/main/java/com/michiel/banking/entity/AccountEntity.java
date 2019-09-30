package com.michiel.banking.entity;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ACCOUNT")
@Data
@NoArgsConstructor
public class AccountEntity {
  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private long id;

  @ManyToMany(mappedBy = "accounts")
  private List<CustomerEntity> customers;

  @ManyToOne
  @JoinColumn(name = "bank_id")
  private BankEntity bank;

  @OneToMany(mappedBy = "toAccount")
  private List<TransactionEntity> transactionsTo;

  @OneToMany(mappedBy = "fromAccount")
  private List<TransactionEntity> transactionsFrom;

  private long balance;
  private AccountType type;

  public List<Long> getIds() {
    return this.customers.stream().map(customer -> customer.getId()).collect(Collectors.toList());
  }
}
