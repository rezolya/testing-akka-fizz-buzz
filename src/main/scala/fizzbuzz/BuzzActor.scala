package fizzbuzz

import akka.actor.{Props, ActorRef, Actor}

object BuzzActor {
  def props(parent: ActorRef) = Props(classOf[BuzzActor], parent)
}

class BuzzActor(parent: ActorRef) extends Actor {
  import FizzBuzzMessages._

  def receive = {
    case r @ Request(num, _, _) =>
      if(isDivisibleByFive(num)){
        parent ! Reply(Right("Buzz"), r)
      }
      else {
        parent ! Reply(Left(num), r)
      }
    case x: Any =>
      parent ! UnknownMessageType(x.toString)
  }

  def isDivisibleByFive (num: Int): Boolean = {
    num % 5 == 0
  }
}