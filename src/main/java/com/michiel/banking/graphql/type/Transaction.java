package com.michiel.banking.graphql.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.michiel.banking.entity.TransactionType;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "id",
    "toId",
    "fromId",
    "amount",
    "type",
    "success"
})
public class Transaction {
  @JsonProperty("id")
  Long id;

  @JsonProperty("toId")
  Long toId;

  @JsonProperty("fromId")
  Long fromId;

  @JsonProperty("amount")
  Long amount;

  @JsonProperty("type")
  TransactionType type;

  @JsonProperty("success")
  Boolean success;
}
