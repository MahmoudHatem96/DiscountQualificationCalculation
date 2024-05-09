ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "DiscountQualificationCalculation",
    libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.14.1",
    libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.14.1"
  )
