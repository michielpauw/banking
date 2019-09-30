package com.michiel.banking.rest.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.michiel.banking.entity.AccountType;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "bank",
    "type"
})
public class NewAccountInput {
  @JsonProperty(value = "type")
  @NotNull
  AccountType type;

  @JsonProperty(value = "customer-id")
  @NotNull
  Long customerId;

  @JsonProperty(value = "bank-id")
  @NotNull
  Long bankId;
}
