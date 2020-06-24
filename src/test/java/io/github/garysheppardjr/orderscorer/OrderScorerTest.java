/**
 * Copyright 2020 Gary L. Sheppard, Jr.
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.garysheppardjr.orderscorer;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OrderScorerTest {

  private static OrderScorer scorer2;
  private static OrderScorer scorer3;
  private static OrderScorer scorer4;

  @BeforeAll
  public static void beforeAll() {
    scorer2 = OrderScorer.newInstance(List.of("zero", "one"));
    scorer3 = OrderScorer.newInstance(List.of("zero", "one", "two"), false);
    scorer4 = OrderScorer.newInstance(List.of("1", "2", "3"), false);
  }

  @Test
  public void testNewInstanceWithBadInput() {
    assertThrows(NullPointerException.class, () -> {
      OrderScorer.newInstance(null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      OrderScorer.newInstance(List.of());
    });
    assertThrows(IllegalArgumentException.class, () -> {
      OrderScorer.newInstance(List.of("one element"));
    });
  }

  private void checkScore(Score score, float percentCorrect, int correctPairCount, int points) {
    assertEquals(percentCorrect, score.getPercentCorrect());
    assertEquals(correctPairCount, score.getCorrectPairCount());
    assertEquals(points, score.getPoints());
  }

  @Test
  public void testScore() {
    Score score;

    checkScore(scorer2.getScore(List.of("zero", "one"), 10), 1, 1, 10);
    checkScore(scorer2.getScore(List.of("one", "zero"), 20), 0, 0, 0);
    checkScore(scorer2.getScore(List.of("zero", "One"), 32), 0, 0, 0);

    checkScore(scorer3.getScore(List.of("zero", "One", "two"), 97), 1, 3, 97);
    checkScore(scorer3.getScore(List.of("two", "one", "zero"), 98), 0, 0, 0);
    checkScore(scorer3.getScore(List.of("one", "two", "zero"), 100), 1f / 3f, 1, 33);
    checkScore(scorer3.getScore(List.of("zero", "two", "one"), 150), 2f / 3f, 2, 100);

    checkScore(scorer4.getScore(List.of("5", "2", "3"), 70), 1f / 3f, 1, 23);
  }

}
