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
    "ask fizz and buzz actors to check the number and combine the results" in {
      val fizzProbe = TestProbe("fizzActor")
      val buzzProbe = TestProbe("buzzActor")

      val props: Props = CombinatorActor.props(Some(fizzProbe.ref), Some(buzzProbe.ref))
      val combinatorActor = system.actorOf(props)

      val fizzActor = system.actorOf(Props[FizzActor])
      val buzzActor = system.actorOf(Props[BuzzActor])

      combinatorActor ! 5

      val correctMessageReceived: PartialFunction[Any, Unit] = {
        case r: Request => r.number should be(5)
      }
      fizzProbe.expectMsgPF()(correctMessageReceived)
      fizzProbe.forward(fizzActor)
      buzzProbe.expectMsgPF()(correctMessageReceived)
      fizzProbe.forward(buzzActor)

      expectMsg("Buzz")
    }

    "correctly combine the results for number 15" in {
      ???
    }
  }
}
