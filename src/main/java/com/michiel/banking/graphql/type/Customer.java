package com.michiel.banking.graphql.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "id",
    "name",
    "age",
    "accounts"
})
@JsonInclude(Include.NON_NULL)
public class Customer {

  @JsonProperty("id")
  Long id;

  @JsonProperty("name")
  String name;

  @JsonProperty("age")
  Integer age;

  @JsonProperty("accounts")
  List<Account> accounts;
}
