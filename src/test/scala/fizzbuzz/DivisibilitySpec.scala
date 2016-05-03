package fizzbuzz

import org.scalatest.{Matchers, WordSpec}

class DivisibilitySpec extends WordSpec with Matchers with Divisibility{

  //TODO: 0.Test the methods that check divisibility in Fizz and Buzz
  //Note: you may have to refactor the actors
  "Divisibility trait" should {
    "determine if number is divisible by 5" in {
      isDivisibleByFive(5) should be(true)
      isDivisibleByFive(9) should be(false)
      isDivisibleByFive(15) should be(true)
      isDivisibleByFive(0) should be(true)
    }

    "determine if number is divisible by 3" in {
      isDivisibleByThree(5) should be(false)
      isDivisibleByThree(9) should be(true)
      isDivisibleByThree(15) should be(true)
      isDivisibleByThree(0) should be(true)
    }
  }
}

