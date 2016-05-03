package fizzbuzz

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import fizzbuzz.CombinatorActor.GetPending
import fizzbuzz.FizzBuzzMessages.{Reply, Request}
import org.scalatest.{Matchers, WordSpecLike}

/*
  This test is multi-threaded
 */
class CombinatorActorItSpec extends TestKit(ActorSystem("TestSystem"))
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll
  with ImplicitSender
{
  "Combinator actor" should {
    "store the pending requests" in {
      val props: Props = CombinatorActor.props()
      val combinatorActor = system.actorOf(props)

      combinatorActor ! 4
      combinatorActor ! GetPending

      expectMsgPF(){
        case pending: Map[Request, Option[Reply]] =>
          pending.size should be(1)
          pending.head._2 should be(None)
      }

      //OR:
      //val request = Request(4, testActor, 0)
      //val expectedPending = Map(request -> None )
      //expectMsg(expectedPending)

      //OR:
      //val message = fizzProbe.expectMsgClass(Request.getClass) //NOT: classOf[Request]
      //message.size should be(1)
      //message.head._2 should be(None)
    }

    //TODO: 1. Implement the tests in a multi-threaded way
    "update pending request after receiving one reply" in {
      ???
    }

    "remove the processed request from pending" in {
      ???
    }
  }
}
