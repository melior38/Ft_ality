ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

ThisBuild / logLevel := Level.Error

Global / parallelExecution := true

Compile / compile / logLevel := Level.Error



lazy val root = (project in file("."))
  .settings(
    name := "ft_ality"
  )
