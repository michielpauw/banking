package com.michiel.banking.rest;

import com.michiel.banking.entity.AccountType;

@FunctionalInterface
public interface AccountTypeFunctionalInterface {
  public boolean typeFilter(AccountType type);
}
