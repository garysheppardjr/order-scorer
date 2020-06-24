/**
 * Copyright 2020 Gary L. Sheppard, Jr.
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.garysheppardjr.orderscorer;

import java.util.Arrays;

/**
 * A command-line interface for the order scorer.
 */
public class OrderScorerCli {

  /**
   * Runs the command-line interface for the order scorer.<br>
   * <br>
   * Arguments: {@code CORRECT_ANSWERS PROVIDED_ANSWERS [POINTS_AVAILABLE]}<br>
   * <br> {@code POINTS_AVAILABLE} default is 100.<br>
   * <br>
   * Example:
   * {@code "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15" "6,15,2,13,8,3,4,5,11,1,14,10,9,12,7" 10}<br>
   * Output:
   * {@code Score: Correct Pairs: 56; Percent Correct: 0.53333336; Points: 5 (out of 10)}
   *
   * @param args the command-line arguments: correct answers list (in quotes),
   * provided answers list (in quotes), and points available (optional)
   */
  public static void main(String[] args) {
    if (2 <= args.length) {
      var correctAnswers = Arrays.asList(args[0].split("[^\\w\\d]+"));
      var providedAnswers = Arrays.asList(args[1].split("[^\\w\\d]+"));
      var scorer = OrderScorer.newInstance(correctAnswers, false);
      var pointsAvailable = 3 <= args.length ? Integer.parseInt(args[2]) : 100;
      var score = scorer.getScore(providedAnswers, pointsAvailable);
      System.out.println("Correct answers: " + correctAnswers);
      System.out.println("Provided answers: " + providedAnswers);
      System.out.println("Score: " + score + " (out of " + pointsAvailable + ")");
    }
  }

}
