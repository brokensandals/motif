package com.leacox.motif.pattern;

import com.leacox.motif.caseclass.Case2;
import com.leacox.motif.function.Function2;
import com.leacox.motif.tuple.Tuple2;

import com.google.auto.value.AutoValue;

/**
 * @author John Leacox
 */
@AutoValue
public abstract class Animal implements Case2<String, Integer> {
  public static Animal create(String name, int numberOfLegs) {
    return new AutoValue_Animal(name, numberOfLegs);
  }

  abstract String name();

  abstract int numberOfLegs();

  @Override
  public Tuple2<String, Integer> extract() {
    return Tuple2.of(name(), numberOfLegs());
  }

  public static <R> Pattern<Animal, R> caseAnimal(
      String name, Integer numberOfLegs, Function2<String, Integer, R> function) {
    return CaseClassPatterns.case2(Animal.class, name, numberOfLegs, function);
  }
}
