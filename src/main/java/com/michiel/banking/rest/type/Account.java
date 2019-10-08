package com.michiel.banking.rest.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.michiel.banking.entity.AccountType;
import java.util.List;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "id",
    "customerIds",
    "balance",
    "type",
    "bank",
    "bankId"
})
@JsonInclude(Include.NON_NULL)
public class Account {

  @JsonProperty("id")
  Long id;

  @JsonProperty("customerIds")
  List<Long> customerIds;

  @JsonProperty("balance")
  Long balance;

  @JsonProperty("type")
  AccountType type;

  @JsonProperty("bank")
  String bank;

  @JsonProperty("bankId")
  Long bankId;
}
