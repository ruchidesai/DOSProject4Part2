name := "TwitterSystem"
 
version := "0.1-SNAPSHOT"
 
scalaVersion := "2.10.3"
 
resolvers ++= Seq(
    "spray repo" at "http://repo.spray.io/"
)
 
libraryDependencies += "org.scala-sbt" % "command" % "0.13.0"
 
libraryDependencies ++= {
    val akkaV = "2.3.0"
    val sprayV = "1.3.1"
    Seq(
        "io.spray" % "spray-can" % sprayV,
        "io.spray" % "spray-routing" % sprayV,
        "com.typesafe.akka" %% "akka-actor" % akkaV
    )
}