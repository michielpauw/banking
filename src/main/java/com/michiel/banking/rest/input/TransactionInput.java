package com.michiel.banking.rest.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.michiel.banking.entity.TransactionType;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "toId",
    "fromId",
    "amount",
    "type"
})
public class TransactionInput {

  @NotNull
  @JsonProperty("toId")
  Long toId;

  @JsonProperty("fromId")
  Long fromId;

  @NotNull
  @JsonProperty("amount")
  Long amount;

  @NotNull
  @JsonProperty("type")
  TransactionType type;
}