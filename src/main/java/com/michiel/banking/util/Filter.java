package com.michiel.banking.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Filter {

  public static <T> List<T> filter(List<T> elements, Predicate<T> predicate) {
    return StreamSupport.stream(elements.spliterator(), false)
        .filter(predicate)
        .collect(Collectors.toList());
  }
}
