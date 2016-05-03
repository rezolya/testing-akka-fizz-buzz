package fizzbuzz

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import fizzbuzz.FizzBuzzMessages.{Reply, Request}
import org.scalatest.{Matchers, WordSpecLike}

class CombinatorActorSpec extends TestKit(ActorSystem("TestSystem"))
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll
{
  //TODO: 3. Test combinator actor synchronously
  "Combinator actor" should {
    "store the pending requests and increase sequence number" in {
      val combinatorActor = TestActorRef(new CombinatorActor())
      val underlyingActor = combinatorActor.underlyingActor

      combinatorActor ! 5

      underlyingActor.pending.size should be(1)
      underlyingActor.pending.get(Request(5, testActor, 0)) should be(None)
      underlyingActor.seqNum should be(1)
    }

    "remove processed requests from pending" in {
      val combinatorActor = TestActorRef(new CombinatorActor())
      val underlyingActor = combinatorActor.underlyingActor

      val previousRequest = Request(3, testActor, 0)
      val previousReply = Some(Reply(Right("Fizz"), previousRequest))
      underlyingActor.pending = Map(previousRequest -> previousReply)
      underlyingActor.pending.size should be(1)

      combinatorActor ! Reply(Left(3), previousRequest)

      underlyingActor.pending.isEmpty should be(true)
    }
  }
}
