ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "sampleKafka"
  )
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.5.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.9"


