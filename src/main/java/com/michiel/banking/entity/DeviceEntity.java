package com.michiel.banking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "DEVICE")
@Data
public class DeviceEntity {
  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private long id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private CustomerEntity customer;

  private String name;
  private DeviceType deviceType;
}
