package com.michiel.banking.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BANK")
@Getter
@Setter
@NoArgsConstructor
public class BankEntity {
  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private long id;

  @OneToMany(mappedBy = "bank")
  private List<AccountEntity> accounts;

  @Column(unique = true)
  private String name;
}
