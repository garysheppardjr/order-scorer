# Order Scorer

Order Scorer scores a set of answers based on their order. It comes with a Java library and one or more applications.

# Command line usage

After cloning the repository:

```
$ mvn install
$ java -jar target/order-scorer-1.0.0.jar "first, second,third" "first, third, second" 10
Correct answers: [first, second, third]
Provided answers: [first, third, second]
Score: Correct Pairs: 2; Percent Correct: 0.6666667; Points: 7 (out of 10)
```

# Developer usage

See [`OrderScorerCli`](src/main/java/io/github/garysheppardjr/order/scorer/OrderScorerCli.java) to see how developers can use leverage the [`OrderScorer`](src/main/java/io/github/garysheppardjr/order/scorer/OrderScorer.java) class.

# License

Order Scorer is available under the Apache License 2.0.