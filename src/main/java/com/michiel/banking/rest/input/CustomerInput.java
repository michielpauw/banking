package com.michiel.banking.rest.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonPropertyOrder({
    "name",
    "age"
})
public class CustomerInput {

  @NotNull
  @JsonProperty("name")
  String name;

  @JsonProperty("age")
  Integer age;
}
