package com.michiel.banking.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

public class Query implements GraphQLQueryResolver {

  private final String test;

  public Query() {
    this.test = "Hallo";
  }
}
