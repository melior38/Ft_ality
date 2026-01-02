ThisBuild / scalaVersion := "3.3.7"

ThisBuild / version      := "0.1.0-SNAPSHOT"

ThisBuild / organization := "com.ft"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  // "-Xfatal-warnings" uncomment if warnings should stop compiling (could imply that some librairy cant compile)
)

lazy val root = project
  .in(file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "ft_ality",
    Compile / mainClass := Some("com.ft.Main")
  )