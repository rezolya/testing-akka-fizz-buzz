package fizzbuzz

import akka.testkit.TestKit
import org.scalatest.{WordSpecLike, BeforeAndAfterAll}


trait StopSystemAfterAll extends BeforeAndAfterAll {
  this: TestKit with WordSpecLike =>
  override protected def afterAll() = {
    super.afterAll()
    TestKit.shutdownActorSystem(system)
  }
}
