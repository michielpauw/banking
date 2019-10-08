package com.michiel.banking.graphql.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "id",
    "name",
    "acounts"
})
@JsonInclude(Include.NON_NULL)
public class Bank {

  @JsonProperty("id")
  Long id;

  @JsonProperty("name")
  String name;

  @JsonProperty("accounts")
  @JsonIgnoreProperties({"bank", "customers"})
  Iterable<Account> accounts;
}
