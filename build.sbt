name := """crud"""
organization := "crud"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

scalaVersion := "2.12.4"

scalacOptions += "-Ypartial-unification" // 2.11.9+

lazy val doobieVersion = "0.5.2"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-hikari" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion,
  "io.monix" %% "monix" % "3.0.0-RC1",
  "com.github.pureconfig" %% "pureconfig" % "0.9.1",
  "com.wix" %% "accord-core" % "0.7.2"
)

swaggerDomainNameSpaces := Seq("clients", "clients.v1", "clients.v2")

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "crud.binders._"
