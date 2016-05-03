package fizzbuzz

import akka.actor.{Props, ActorSystem}
import akka.testkit.{EventFilter, CallingThreadDispatcher, TestActorRef, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.{Matchers, WordSpecLike}

object CombinatorActorLoggingSpec {
  val testSystem = {
    val config = ConfigFactory.parseString(
      """
        akka.loggers = [akka.testkit.TestEventListener]
      """)
    ActorSystem("TestSystemWithTestLogger", config)
  }
}

class CombinatorActorLoggingSpec extends TestKit(CombinatorActorLoggingSpec.testSystem)
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll
{
  "Combinator actor" should {
    "log received input" in {
      val props = CombinatorActor.props()//.withDispatcher(CallingThreadDispatcher.Id) //to make it single-threaded
      val actorRef = system.actorOf(props)

      EventFilter.info(pattern = "Received number to test: *", occurrences = 1)
        .intercept{
          actorRef ! 11
        }
    }

    //TODO: implement this test
    "log received reply" in {
      ???
    }
  }
}
