name := "slick_sample"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7" // or "2.10.4"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "com.typesafe.play" %% "play-slick" % "1.1.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.0",
  "mysql" % "mysql-connector-java" % "5.1.36"
)

fork in Test := false

lazy val root = (project in file(".")).enablePlugins(PlayScala)

routesGenerator := InjectedRoutesGenerator
