package com.michiel.banking.graphql.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "name"
})
public class BankInput {

  @JsonProperty("name")
  @NotNull
  String name;
}
