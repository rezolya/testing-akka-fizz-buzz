package fizzbuzz

import akka.actor.{Props, ActorLogging, Actor}

object CombinatorActor {
  def props() = Props[CombinatorActor]
}

class CombinatorActor extends Actor
  with ActorLogging {
  import fizzbuzz.FizzBuzzMessages._

  val fizzChild = context.actorOf(Props[FizzActor])
  val buzzChild = context.actorOf(Props[BuzzActor])

  var seqNum = 0
  var pending = Map[Request, Option[Reply]]()

  def receive = {
    case i: Input => {
      log.info(s"Received number to test: $i")

      val request = Request(i, sender(), seqNum)
      buzzChild ! request
      fizzChild ! request

      pending = pending + (request -> None)
      seqNum = seqNum + 1
    }
    case reply @ Reply(answer, request) => {
      val previousReplyOpt = pending.get(request) match {
        case Some(option) => option
        case None => throw new Exception(s"Unexpected reply received: $reply")
      }

      previousReplyOpt match {
        case None =>
          pending = pending + (request -> Some(reply)) //update pending requests
        case Some(previousAnswer) =>
          request.sender ! combineAnswers(previousAnswer.answer, answer)
          pending = pending - request // remove processed request
      }
    }
  }

  def combineAnswers(a1: Answer, a2: Answer): String = {
    (a1, a2) match {
      case (Left(n1), Left(n2)) => n1.toString
      case (Left(n1), Right(s2)) => s2
      case (Right(s1), Left(n2)) => s1
      case (Right(s1), Right(s2)) => s1 + s2
    }
  }
}
