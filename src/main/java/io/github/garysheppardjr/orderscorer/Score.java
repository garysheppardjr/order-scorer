/**
 * Copyright 2020 Gary L. Sheppard, Jr.
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.garysheppardjr.orderscorer;

import java.math.BigDecimal;

/**
 * A score produced by the order scorer.
 */
public class Score {

  private final int correctPairCount;
  private final float percentCorrect;
  private final int points;

  private Score(int correctPairCount, int totalPairCount, int totalPointsAvailable) {
    this.correctPairCount = correctPairCount;
    this.percentCorrect = ((float) correctPairCount) / ((float) totalPairCount);
    this.points = Math.round(percentCorrect * (float) totalPointsAvailable);
  }

  /**
   * Creates a new unmodifiable score object.
   *
   * @param correctPairCount the number of correct pairs.
   * @param totalPairCount the total number of pairs.
   * @param totalPointsAvailable the number of points available.
   * @return a new unmodifiable score object.
   */
  public static Score newInstance(int correctPairCount, int totalPairCount, int totalPointsAvailable) {
    return new Score(correctPairCount, totalPairCount, totalPointsAvailable);
  }

  /**
   * Returns the number of correct pairs.
   *
   * @return the number of correct pairs.
   */
  public int getCorrectPairCount() {
    return correctPairCount;
  }

  /**
   * Returns the percentage of answers given that were correct.
   *
   * @return the percentage of answers given that were correct.
   */
  public double getPercentCorrect() {
    return percentCorrect;
  }

  /**
   * Returns the number of points awarded.
   *
   * @return the number of points awarded.
   */
  public int getPoints() {
    return points;
  }

  @Override
  public String toString() {
    return "Correct Pairs: " + getCorrectPairCount() + "; Percent Correct: " + percentCorrect + "; Points: " + points;
  }

  @Override
  public boolean equals(Object obj) {
    if (null == obj || !(this.getClass().isAssignableFrom(obj.getClass()))) {
      return false;
    } else {
      Score other = (Score) obj;
      return this.getCorrectPairCount() == other.getCorrectPairCount()
          && this.getPoints() == other.getPoints()
          && BigDecimal.valueOf(this.getPercentCorrect()).equals(other.getPercentCorrect());
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 11 * hash + this.correctPairCount;
    hash = 11 * hash + Float.floatToIntBits(this.percentCorrect);
    hash = 11 * hash + this.points;
    return hash;
  }

}
