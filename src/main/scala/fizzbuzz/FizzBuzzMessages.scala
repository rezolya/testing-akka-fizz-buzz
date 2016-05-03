package fizzbuzz

import akka.actor.ActorRef

object FizzBuzzMessages {
  type Input = Int
  type Answer = Either[Int, String]
  case class Reply(answer: Answer, request: Request)
  case class Request(number: Int, sender: ActorRef, seqNum: Int)
  case class UnknownMessageType(message: String)
}