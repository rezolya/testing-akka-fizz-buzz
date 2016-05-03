package fizzbuzz

import akka.actor.Actor

class BuzzActor extends Actor with Divisibility {
  import FizzBuzzMessages._

  def receive = {
    case r @ Request(num, _, _) =>
      if(isDivisibleByFive(num)){
        sender() ! Reply(Right("Buzz"), r)
      }
      else {
        sender() ! Reply(Left(num), r)
      }
    case _ => context.parent ! "unknown message type"
  }
}