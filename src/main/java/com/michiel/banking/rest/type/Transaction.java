package com.michiel.banking.rest.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "id",
    "to_id",
    "from_id",
    "amount",
    "type",
    "success"
})
public class Transaction {
  @JsonProperty("id")
  Long id;

  @JsonProperty("to_id")
  Long toId;

  @JsonProperty("from_id")
  Long fromId;

  @JsonProperty("amount")
  Long amount;

  @JsonProperty("type")
  String type;

  @JsonProperty("success")
  Boolean success;
}
