package fizzbuzz

import akka.actor.{Props, ActorLogging, Actor}
import fizzbuzz.CombinatorActor.GetPending

object CombinatorActor {
  def props() = Props[CombinatorActor]

  case class GetPending()
}

class CombinatorActor extends Actor
  with ActorLogging with Utils {
  import fizzbuzz.FizzBuzzMessages._

  val fizzChild = context.actorOf(FizzActor.props(self))
  val buzzChild = context.actorOf(BuzzActor.props(self))

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
    case GetPending => sender() ! pending
  }
}
