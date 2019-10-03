package com.michiel.banking.graphql;

import com.michiel.banking.graphql.resolver.Mutation;
import com.michiel.banking.graphql.resolver.Query;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfiguration {

  @Bean
  public Query query() {
    return new Query();
  }

  @Bean
  public Mutation mutation() {
    return new Mutation();
  }
}
