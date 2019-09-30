package com.michiel.banking.service.filter;

import com.michiel.banking.entity.AccountType;

@FunctionalInterface
public interface AccountTypeFunctionalInterface {
  public boolean typeFilter(AccountType type);
}
