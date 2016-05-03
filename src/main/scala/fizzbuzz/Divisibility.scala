package fizzbuzz

trait Divisibility {

  def isDivisibleByThree (num: Int): Boolean = {
    num % 3 == 0
  }

  def isDivisibleByFive (num: Int): Boolean = {
    num % 5 == 0
  }
}
