package fizzbuzz

import akka.actor.{ActorRef, Props, ActorLogging, Actor}
import fizzbuzz.CombinatorActor.GetPending

object CombinatorActor {
  def props(fizzActor: Option[ActorRef] = None, buzzActor: Option[ActorRef] = None) =
    Props(new CombinatorActor(fizzActor, buzzActor))

  case class GetPending()
}

class CombinatorActor(fizzActor: Option[ActorRef],
                      buzzActor: Option[ActorRef])
  extends Actor
  with ActorLogging with Utils {
  import fizzbuzz.FizzBuzzMessages._

  val fizzChild = fizzActor.getOrElse(context.actorOf(Props[FizzActor]))
  val buzzChild = buzzActor.getOrElse(context.actorOf(Props[BuzzActor]))

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
