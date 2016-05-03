package fizzbuzz

import akka.actor.Actor

class BuzzActor extends Actor {
  import FizzBuzzMessages._

  def receive = {
    case r @ Request(num, _, _) =>
      if(isDivisibleByFive(num)){
        context.parent ! Reply(Right("Buzz"), r)
      }
      else {
        context.parent ! Reply(Left(num), r)
      }
    case _ => context.parent ! "unknown message type"
  }

  def isDivisibleByFive (num: Int): Boolean = {
    num % 5 == 0
  }
}