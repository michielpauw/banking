package com.michiel.banking.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil {

  public static <T> List<T> filter(Stream<T> stream, Predicate<T> predicate) {
    return stream.filter(predicate).collect(Collectors.toList());
  }

  public static <T> List<T> filter(Iterable<T> elements, Predicate<T> predicate) {
    return filter(getStream(elements), predicate);
  }

  public static <T> Stream<T> getStream(Iterable<T> elements) {
    return StreamSupport.stream(elements.spliterator(), true);
  }
}
