package fizzbuzz

import akka.actor.{ActorRef, ActorRefFactory, ActorSystem, Props}
import akka.testkit._
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
    val fizzProbe = TestProbe("fizzActor")
    val buzzProbe = TestProbe("buzzActor")

    val fizzMaker = (_: ActorRefFactory) => fizzProbe.ref
    val buzzMaker = (_: ActorRefFactory) => buzzProbe.ref

    val props: Props = CombinatorActor.props(fizzMaker, buzzMaker)
    val combinatorActor = system.actorOf(props)

    "ask fizz and buzz actors to check the number" in {
      combinatorActor ! 5

      val correctMessageReceived: PartialFunction[Any, Unit] = {
        case r: Request => r.number should be(5)
      }
      fizzProbe.expectMsgPF()(correctMessageReceived)
      buzzProbe.expectMsgPF()(correctMessageReceived)
    }

    "combine answers correctly" in {
      fizzProbe.setAutoPilot(new TestActor.AutoPilot {
        def run(sender: ActorRef, msg: Any) =
          msg match {
            case r: Request =>
              sender ! Reply(Left(5), r)
              TestActor.KeepRunning
            case "stop" => TestActor.NoAutoPilot
          }
      })
      buzzProbe.setAutoPilot(new TestActor.AutoPilot {
        def run(sender: ActorRef, msg: Any) =
          msg match {
            case r: Request =>
              sender ! Reply(Right("Buzz"), r)
              TestActor.KeepRunning
            case "stop" => TestActor.NoAutoPilot
          }
      })
      combinatorActor ! 5
      expectMsg("Buzz")
      combinatorActor ! 10
      expectMsg("Buzz")

      fizzProbe.ref ! "stop"
      buzzProbe.ref ! "stop"
    }
  }
}
