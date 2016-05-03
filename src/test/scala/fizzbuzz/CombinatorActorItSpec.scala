package fizzbuzz

import akka.actor.{Actor, ActorSystem, Props}
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
    "combine answers correctly" in {
      val props: Props = CombinatorActor.props(Props[MockedFizz], Props[MockedBuzz])
      val combinatorActor = system.actorOf(props)
      combinatorActor ! 15
      expectMsgAnyOf("FizzBuzz", "BuzzFizz")
    }
  }
}

class MockedFizz extends Actor {
  def receive = {
    case r: Request =>
      sender() ! Reply(Right("Fizz"), r)
  }
}
class MockedBuzz extends Actor {
  def receive = {
    case r: Request =>
      sender() ! Reply(Right("Buzz"), r)
  }
}