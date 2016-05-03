package fizzbuzz

import akka.actor.{Props, ActorRef, Actor}
import fizzbuzz.FizzBuzzMessages.{UnknownMessageType, Request, Reply}

object FizzActor {
  def props(parent: ActorRef) = Props(classOf[FizzActor], parent)
}

class FizzActor(parent: ActorRef) extends Actor {
  def receive = {
    case r @ Request(num, _, _) =>
      if(isDivisibleByThree(num))
        parent ! Reply(Right("Fizz"), r)
      else
        parent ! Reply(Left(num), r)
    case x: Any =>
      parent ! UnknownMessageType(x.toString)
  }

  def isDivisibleByThree (num: Int): Boolean = {
    num % 3 == 0
  }
}