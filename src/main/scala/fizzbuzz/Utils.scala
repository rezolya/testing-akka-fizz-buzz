package fizzbuzz

import fizzbuzz.FizzBuzzMessages._

trait Utils {
  def combineAnswers(a1: Answer, a2: Answer): String = {
    (a1, a2) match {
      case (Left(n1), Left(n2)) => n1.toString
      case (Left(n1), Right(s2)) => s2
      case (Right(s1), Left(n2)) => s1
      case (Right(s1), Right(s2)) => s1 + s2
    }
  }
}
