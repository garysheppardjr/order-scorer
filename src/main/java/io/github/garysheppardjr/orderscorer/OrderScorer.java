/**
 * Copyright 2020 Gary L. Sheppard, Jr.
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.garysheppardjr.orderscorer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that scores a set of answers based on their order.<br>
 * <br>
 * Every answer is compared with every other answer to determine the total
 * score. Each comparison counts equally in the final scoring.<br>
 * <br>
 * For example, consider an order scorer initialized with the following list of
 * correct answers:<br>
 * <br>
 * {@code ["Declaration of Independence", "Articles of Confederation", "Constitution", "War of 1812"]}<br>
 * <br>
 * If this order scorer is called with the following list:
 * {@code ["Declaration of Independence", "Constitution", "Articles of Confederation", "War of 1812"]}<br>
 * <br>
 * Then the scoring is as follows:
 * <ul>
 * <li>CORRECT: Declaration of Independence comes before Constitution</li>
 * <li>CORRECT: Declaration of Independence comes before Articles of
 * Confederation</li>
 * <li>CORRECT: Declaration of Independence comes before War of 1812</li>
 * <li>INCORRECT: Constitution comes before Articles of Confederation</li>
 * <li>CORRECT: Constitution comes before War of 1812</li>
 * <li>CORRECT: Articles of Confederation comes before War of 1812</li>
 * </ul>
 * Here the getScore would be 5 out of 6, or 0.8333...
 */
public class OrderScorer {

  private final List<String> correctAnswers;
  private final boolean caseSensitive;

  private OrderScorer(List<String> correctAnswers, boolean caseSensitive) {
    if (correctAnswers.size() < 2 || !isListMembersDistinct(correctAnswers)) {
      throw new IllegalArgumentException("List of correct answers has duplicate values: " + correctAnswers);
    } else {
      this.caseSensitive = caseSensitive;
      this.correctAnswers = convertCase(correctAnswers);
    }
  }

  /**
   * Returns a new unmodifiable instance of the order scorer with case-sensitive
   * behavior.
   *
   * @param correctAnswers a list of at least two answers in their correct
   * order.
   * @return a new instance of the order scorer.
   */
  public static OrderScorer newInstance(List<String> correctAnswers) {
    return newInstance(correctAnswers, true);
  }

  /**
   * Returns a new instance of the order scorer.
   *
   * @param correctAnswers a list of at least two answers in their correct
   * order.
   * @param caseSensitive false if the order scorer will ignore case and true
   * otherwise.
   * @return a new instance of the order scorer.
   */
  public static OrderScorer newInstance(List<String> correctAnswers, boolean caseSensitive) {
    return new OrderScorer(correctAnswers, caseSensitive);
  }

  private boolean isListMembersDistinct(List<String> list) {
    return list.size() == list.stream().distinct().count();
  }

  private List<String> convertCase(List<String> list) {
    return list.stream().map(item -> caseSensitive ? item : item.toLowerCase()).collect(Collectors.toList());
  }

  private float getNumberOfPairs(int numberOfElements) {
    var pairs = 0;
    for (var i = 2; i <= numberOfElements; i++) {
      pairs += (i - 1);
    }
    return pairs;
  }

  /**
   * Scores the provided list of answers against the list of correct answers
   * that this order scorer contains.
   *
   * @param answers the list of answers to be scored.
   * @param pointsAvailable the number of points available.
   * @return the score of the provided answers.
   */
  public Score getScore(List<String> answers, int pointsAvailable) {
    answers = convertCase(answers);
    var countCorrect = countCorrect(answers);
    var pairCount = getNumberOfPairs(answers.size());
    return Score.newInstance(Math.round(countCorrect), Math.round(pairCount), pointsAvailable);
  }

  private float countCorrect(List<String> answers) {
    // Compare element 0 to all other elements
    if (answers.size() < 2) {
      return 0;
    } else if (!isListMembersDistinct(answers)) {
      throw new IllegalArgumentException("List of answers has duplicate values: " + answers);
    } else {
      int countCorrect = 0;
      final var correctIndexOfFirst = this.correctAnswers.indexOf(answers.get(0));
      if (correctIndexOfFirst >= 0) {
        for (var i = 1; i < answers.size(); i++) {
          final var correctIndexOfThis = this.correctAnswers.indexOf(answers.get(i));
          if (correctIndexOfFirst < correctIndexOfThis) {
            countCorrect++;
          }
        }
      }
      return countCorrect + countCorrect(answers.subList(1, answers.size()));
    }
  }

}
