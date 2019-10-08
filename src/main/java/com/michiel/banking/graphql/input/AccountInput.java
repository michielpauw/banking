package com.michiel.banking.graphql.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.michiel.banking.entity.AccountType;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "customerId",
    "bankId",
    "type"
})
public class AccountInput {
  @JsonProperty(value = "type")
  @NotNull
  AccountType type;

  @JsonProperty(value = "customerId")
  @NotNull
  Long customerId;

  @JsonProperty(value = "bankId")
  @NotNull
  Long bankId;
}
