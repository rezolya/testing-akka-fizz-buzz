package fizzbuzz

import org.scalatest.{Matchers, WordSpec}

class UtilsSpec extends WordSpec with Matchers
  with Utils{

  "Utils" should {
    "combine the results correctly" in {
      combineAnswers(Left(4), Left(4)) should be("4")
      combineAnswers(Right("Fizz"), Left(3)) should be("Fizz")
      combineAnswers(Left(10), Right("Buzz")) should be("Buzz")
      combineAnswers(Left(9), Right("Fizz")) should be("Fizz")
      combineAnswers(Right("Buzz"), Right("Fizz")) should be("BuzzFizz")
    }
  }
}
