Steps to execute:

1) Copy the files to one of the lin116 servers.

2) Open 6 instances of:
     lin116-00.cise.ufl.edu	(will serve as Server)
     lin116-01.cise.ufl.edu	(will serve as Client1)
     lin116-02.cise.ufl.edu	(will serve as Client2)
     lin116-03.cise.ufl.edu	(will serve as Client3)
     lin116-04.cise.ufl.edu	(will serve as Client4)
     lin116-05.cise.ufl.edu	(will serve as Client5)

3) cd to scala/bin (assuming you have placed your own scala and akka folders on these machines)

4) On server type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Server.scala 100000 128.227.248.195 128.227.248.196 128.227.248.197 128.227.248.198 128.227.248.199

5) On Client1 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 128.227.248.194 1

6) On Client2 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 128.227.248.194 2

7) On Client3 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 128.227.248.194 3

8) On Client4 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 128.227.248.194 4

9) On Client5 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 128.227.248.194 5

Program will stop automatically after 5 minutes.