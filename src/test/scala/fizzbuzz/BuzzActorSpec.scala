package fizzbuzz

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import fizzbuzz.FizzBuzzMessages.{Reply, Request}
import org.scalatest.{Matchers, WordSpecLike}

class BuzzActorSpec extends TestKit(ActorSystem("TestSystem"))
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll
{
  //TODO: 2. test actor with telling syncronously
  "BuzzActor" should {
    "reply with 'Buzz' if number is divisible by 5" in {
      ???
    }

    "reply with original number if it is not divisible by 5" in {
      ???
    }
  }
}
