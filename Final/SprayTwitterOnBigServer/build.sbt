name := "TwitterSystem"
 
version := "0.1-SNAPSHOT"
 
scalaVersion := "2.10.4"
 
resolvers ++= Seq(
    "spray repo" at "http://repo.spray.io/"
)
 
libraryDependencies ++= {
    val akkaV = "2.3.0"
    val sprayV = "1.3.1"
    Seq(
        "io.spray" % "spray-can" % sprayV,
        "io.spray" % "spray-routing" % sprayV,
		"io.spray" %% "spray-json" % sprayV,
		"com.typesafe.akka" %% "akka-actor" % akkaV
    )
}