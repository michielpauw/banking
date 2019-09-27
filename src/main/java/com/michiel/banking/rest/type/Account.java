package com.michiel.banking.rest.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    "customer-ids",
    "balance",
    "type",
    "bank"
})
@JsonInclude(Include.NON_NULL)
public class Account {

  @JsonProperty("id")
  Long id;

  @JsonProperty("customer-ids")
  List<Long> customerIds;

  @JsonProperty("balance")
  Long balance;

  @JsonProperty("type")
  AccountType type;

  @JsonProperty("bank")
  @JsonIgnoreProperties({"accounts"})
  String bank;
}
