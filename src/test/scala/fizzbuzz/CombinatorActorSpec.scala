package fizzbuzz

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{Matchers, WordSpecLike}

class CombinatorActorSpec extends TestKit(ActorSystem("TestSystem"))
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll
{
  //TODO: 3. Test combinator actor synchronously
  "Combinator actor" should {
    "store the pending requests and increase sequence number" in {
      ???
    }

    "remove processed requests from pending" in {
      ???
    }
  }
}
