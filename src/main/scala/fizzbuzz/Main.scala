package fizzbuzz

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
  implicit val timeout = Timeout(5 seconds)
  val system = ActorSystem("FizzBuzzSystem")

  val combinatorActor = system.actorOf(Props[CombinatorActor])
  val futures: IndexedSeq[Future[Any]] = (0 to 100).map{
    combinatorActor ? _
  }
  val future = Future.sequence(futures)
  val result: IndexedSeq[Any] = Await.result(future, 10 seconds)
  result.foreach(println(_))
}
