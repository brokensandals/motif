package com.leacox.motif.pattern;

import com.leacox.motif.function.Consumer2;
import com.leacox.motif.function.Consumer3;
import com.leacox.motif.function.Function2;
import com.leacox.motif.function.Function3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author John Leacox
 */
public interface Pattern<T, R> {
  boolean matches(T value);

  R apply(T value);

  static <T, R> Pattern<T, R> of(Function<T, Boolean> matcher, Function<T, R> function) {
    return new Pattern<T, R>() {
      @Override
      public boolean matches(T value) {
        return matcher.apply(value);
      }

      @Override
      public R apply(T value) {
        return function.apply(value);
      }
    };
  }

  static <T> Matching<T> matching(final Function<T, Boolean> matcher) {
    return new Matching<T>(matcher);
  }

  class Matching<T> {
    private final Function<T, Boolean> matcher;

    Matching(Function<T, Boolean> matcher) {
      this.matcher = matcher;
    }

    public <R> Pattern<T, R> is(Supplier<R> supplier) {
      return Pattern.of(matcher, t -> supplier.get());
    }

    public ConsumablePattern<T> does(Runnable runnable) {
      return ConsumablePattern.of(matcher, t -> runnable.run());
    }

    public <A> Taking1<A> taking1(Function<T, A> transformer1) {
      return new Taking1<A>(transformer1);
    }

    public <A, B> Taking2<A, B> taking2(Function<T, A> transformer1, Function<T, B> transformer2) {
      return new Taking2<A, B>(transformer1, transformer2);
    }

    public <A, B, C> Taking3<A, B, C> taking3(Function<T, A> transformer1, Function<T, B> transformer2, Function<T, C> transformer3) {
      return new Taking3<A, B, C>(transformer1, transformer2, transformer3);
    }

    class Taking1<A> {
      private final Function<T, A> transformer1;

      Taking1(Function<T, A> transformer1) {
        this.transformer1 = transformer1;
      }

      public <R> Pattern<T, R> is(Function<A, R> function) {
        return Pattern.of(matcher, t -> function.apply(transformer1.apply(t)));
      }

      public ConsumablePattern<T> does(Consumer<A> consumer) {
        return ConsumablePattern.of(matcher, t -> consumer.accept(transformer1.apply(t)));
      }
    }

    class Taking2<A, B> {
      private final Function<T, A> transformer1;
      private final Function<T, B> transformer2;

      Taking2(Function<T, A> transformer1, Function<T, B> transformer2) {
        this.transformer1 = transformer1;
        this.transformer2 = transformer2;
      }

      public <R> Pattern<T, R> is(Function2<A, B, R> function) {
        return Pattern.of(matcher, t -> function.apply(transformer1.apply(t), transformer2.apply(t)));
      }

      public ConsumablePattern<T> does(Consumer2<A, B> consumer) {
        return ConsumablePattern.of(matcher, t -> consumer.accept(transformer1.apply(t), transformer2.apply(t)));
      }
    }

    class Taking3<A, B, C> {
      private final Function<T, A> transformer1;
      private final Function<T, B> transformer2;
      private final Function<T, C> transformer3;

      Taking3(Function<T, A> transformer1, Function<T, B> transformer2, Function<T, C> transformer3) {
        this.transformer1 = transformer1;
        this.transformer2 = transformer2;
        this.transformer3 = transformer3;
      }

      public <R> Pattern<T, R> is(Function3<A, B, C, R> function) {
        return Pattern.of(matcher, t -> function.apply(transformer1.apply(t), transformer2.apply(t), transformer3.apply(t)));
      }

      public ConsumablePattern<T> does(Consumer3<A, B, C> consumer) {
        return ConsumablePattern.of(matcher, t -> consumer.accept(transformer1.apply(t), transformer2.apply(t), transformer3.apply(t)));
      }
    }
  }
}
