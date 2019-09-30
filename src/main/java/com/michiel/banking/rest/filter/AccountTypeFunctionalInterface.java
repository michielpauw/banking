package com.michiel.banking.rest.filter;

import com.michiel.banking.entity.AccountType;

@FunctionalInterface
public interface AccountTypeFunctionalInterface {
  public boolean typeFilter(AccountType type);
}
