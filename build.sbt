scalaVersion := "2.11.7"

name := "testing-actors"

libraryDependencies ++= List(
  "com.typesafe.akka" %% "akka-actor" % "2.4.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.4" % "test",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

cancelable in Global := true