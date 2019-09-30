package com.michiel.banking.rest.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.michiel.banking.entity.TransactionType;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "to_id",
    "from_id",
    "amount",
    "type"
})
public class TransactionInput {

  @NotNull
  @JsonProperty("to_id")
  Long toId;

  @JsonProperty("from_id")
  Long fromId;

  @NotNull
  @JsonProperty("amount")
  Long amount;

  @NotNull
  @JsonProperty("type")
  TransactionType type;
}