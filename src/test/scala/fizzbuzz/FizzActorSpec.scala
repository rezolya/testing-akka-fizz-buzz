package fizzbuzz

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.{TestActorRef, TestKit}
import akka.util.Timeout
import fizzbuzz.FizzBuzzMessages.{Reply, Request}
import org.scalatest.{Matchers, WordSpecLike}

import scala.concurrent.duration._
import scala.util.Success

/*
  This test is single-threaded
 */
class FizzActorSpec extends TestKit(ActorSystem("TestSystem"))
  with WordSpecLike
  with Matchers
  with StopSystemAfterAll {

  "Fizz actor" should {
    "determine if number is divisible by 3" in {
      val actorRef = TestActorRef[FizzActor]
      val underlyingActor = actorRef.underlyingActor

      underlyingActor.isDivisibleByThree(3) should be(true)
      underlyingActor.isDivisibleByThree(9) should be(true)
      underlyingActor.isDivisibleByThree(19) should be(false)
      underlyingActor.isDivisibleByThree(0) should be(true)
    }

    "reply with 'Fizz' if number is divisible by 3" in {
      val actorRef = TestActorRef(new FizzActor)
      implicit val timeout = Timeout(5 seconds)
      val request = Request(9, null, 1)

      val future = actorRef ? request
      val futureResult = future.value.get

      val expectedReply = Reply(Right("Fizz"), request)
      futureResult should be(Success(expectedReply))
    }

    //TODO: 1.test actor with asking synchronously
    "reply with original number if it is not divisible by 3" in {
      val actorRef = TestActorRef(new FizzActor)
      implicit val timeout = Timeout(5 seconds)
      val request = Request(5, null, 1)

      val future = actorRef ? request
      val futureResult = future.value.get

      val expectedReply = Reply(Left(5), request)
      futureResult should be(Success(expectedReply))
    }
  }
}
