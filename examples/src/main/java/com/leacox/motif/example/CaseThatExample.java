package com.leacox.motif.example;

import static com.leacox.motif.Motif.match;
import static com.leacox.motif.pattern.CaseThatPatterns.caseEq;
import static com.leacox.motif.pattern.OrElsePattern.orElse;

/**
 * @author John Leacox
 */
public class CaseThatExample {
  public static void main(String[] args) {
    new CaseThatExample().run();
  }

  public void run() {
    Object pi = Math.PI;

    String result = match(pi).on(
        caseEq(42).is(() -> "a magic no."),
        caseEq("Hello!").is(() -> "a greet"),
        caseEq(Math.PI).is(() -> "another magic no."),
        orElse("something else")
    );

    System.out.println("Matching Result: " + result);
  }
}
