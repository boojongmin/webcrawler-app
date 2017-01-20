name := "contentcrawler-app"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.16"
libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.4.16"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.4.16"
libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.6"
libraryDependencies += "org.scaldi" %% "scaldi" % "0.5.8"
libraryDependencies += "org.scaldi" %% "scaldi-akka" % "0.5.8"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"


libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.6.Final"
libraryDependencies += "org.jinq" % "api" % "1.8.15"
libraryDependencies += "org.jinq" % "jinq-jpa-scala" % "1.8.15"
libraryDependencies += "com.h2database" % "h2" % "1.4.193"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.8"


