package fizzbuzz

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import fizzbuzz.FizzBuzzMessages.{Reply, Request}
import org.scalatest.{Matchers, WordSpecLike}

class BuzzActorSpec extends TestKit(ActorSystem("TestSystem"))
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll
  with ImplicitSender
{
  //TODO: 2. test actor with telling syncronously
  "BuzzActor" should {
    "reply with 'Buzz' if number is divisible by 5" in {
      val actorRef = TestActorRef(new BuzzActor)
      val request = Request(20, null, 1)

      actorRef ! request

      val expectedReply = Reply(Right("Buzz"), request)
      expectMsg(expectedReply)
    }

    "reply with original number if it is not divisible by 5" in {
      val actorRef = TestActorRef(new BuzzActor)
      val request = Request(4, null, 1)

      actorRef ! request

      val expectedReply = Reply(Left(4), request)
      expectMsg(expectedReply)
    }
  }
}
