package fizzbuzz

import akka.actor.{Actor, Props, ActorSystem}
import akka.testkit.{TestProbe, ImplicitSender, TestActorRef, TestKit}
import fizzbuzz.FizzBuzzMessages.{UnknownMessageType, Reply, Request}
import org.scalatest.{Matchers, WordSpecLike}

class BuzzActorItSpec extends TestKit(ActorSystem("TestSystem"))
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll
  with ImplicitSender
{
  "BuzzActor" should {

    "notify its parent if it receives unexpected message" in {
      val parentActor = TestProbe()
      val buzzActor = system.actorOf(BuzzActor.props(parentActor.ref))

      buzzActor ! "do something"

      parentActor.expectMsg(UnknownMessageType("do something"))
    }

    "send the reply to the parent no matter where the request came from" in {
      val parentActor = TestProbe()
      val buzzActor = system.actorOf(BuzzActor.props(parentActor.ref))

      val request = Request(15, self, 2)
      buzzActor ! request

      parentActor.expectMsg(Reply(Right("Buzz"), request))
    }
  }


}
