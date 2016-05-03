package fizzbuzz

import akka.actor.{Actor, Props, ActorSystem}
import akka.testkit.{TestProbe, ImplicitSender, TestActorRef, TestKit}
import fizzbuzz.FizzBuzzMessages.{Reply, Request}
import org.scalatest.{Matchers, WordSpecLike}

class BuzzActorItSpec extends TestKit(ActorSystem("TestSystem"))
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll
  with ImplicitSender
{
  "A fabricated parent" should {
    "test BuzzActor response" in {
      val proxyActor = TestProbe()
      val parentActor = system.actorOf(Props(
        new Actor {
          val child = context.actorOf(Props[BuzzActor])
          def receive = {
            case x if sender == child =>
              proxyActor.ref forward x
            case x =>
              child forward x
          }
        }))

      val request = Request(20, null, 1)
      proxyActor.send(parentActor, request)

      val expectedReply = Reply(Right("Buzz"), request)
      proxyActor.expectMsg(expectedReply)
    }
  }

  "BuzzActor" should {
    //TODO: implement this using the fabricated parent
    "notify its parent if it receives unexpected message" in {
      ???
    }
  }
}
