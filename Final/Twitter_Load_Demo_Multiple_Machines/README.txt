Steps to execute:

1) On 6 different machines, cd to scala/bin (assuming you have placed your own scala and akka folders on these machines)

4) On server type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Server.scala 100000 <ip-addresses-of-5-clients-separated-by-space>

5) On Client1 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 <ip-address of server> 1

6) On Client2 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 <ip-address of server> 2

7) On Client3 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 <ip-address of server> 3

8) On Client4 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 <ip-address of server> 4

9) On Client5 type the command:

./scala -classpath "<full-path-to>/akka-actor_2.11-2.3.6.jar:<full-path-to>/common_2.10-1.0.jar:<full-path-to>/akka-remote_2.11-2.3.6.jar:<full-path-to>/netty-3.8.0.Final.jar:<full-path-to>/protobuf-java-2.5.0.jar:<full-path-to>/config-1.2.1.jar:<full-path-to>/scala-library-2.11.2.jar " <full-path-to>/Client.scala 100000 <ip-address of server> 5

Program will stop automatically after 5 minutes.