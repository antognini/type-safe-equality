// Project
val projectName = "type-safe-equality"
ThisBuild / organization := "ch.produs"
ThisBuild / organizationName := "produs ag"
ThisBuild / organizationHomepage := None
ThisBuild / description := "Scala 3 type safe equality"
ThisBuild / homepage := Some(url(s"https://github.com/antognini/$projectName"))
ThisBuild / licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / version := "0.6.0"
ThisBuild / versionScheme := Some("semver-spec")
ThisBuild / developers := List(
  Developer(
    id = "LA",
    name = "Luigi Antognini",
    email = "",
    url = url("https://github.com/antognini")
  )
)
ThisBuild / scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/antognini/$projectName"),
    s"scm:git@github.com:antognini/$projectName.git"
  )
)
Global / onChangedBuildSource := ReloadOnSourceChanges


// Compile
ThisBuild / scalaVersion := "3.3.0"
ThisBuild / Compile / scalacOptions := Seq(
  "-encoding", "utf8",
  "-deprecation",
  "-language:scala3",
  "-new-syntax",
  "-indent",
  "-language:strictEquality",
  "-java-output-version", "11"
//  "-Wunused:all"
)


lazy val root = project.in(file(".")).dependsOn(equality).settings(
  name := projectName,
  publish / skip := true
).aggregate(equality, examples)

lazy val equality = project.in(file("equality")).settings(
  publishLocal := publishLocal.dependsOn(clean, Test / test).value,
  publish := publish.dependsOn(clean, Test / test).value,
  name := projectName,
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.16" % "test"
  )
)

lazy val examples = project.in(file("examples")).dependsOn(equality).settings(
  publish / skip := true,
  Compile / scalacOptions += "-Yimports:scala,scala.Predef,java.lang,equality"
)


// Publish
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
sonatypeCredentialHost := "s01.oss.sonatype.org"
credentials ++= Seq(
  Credentials(
    "GnuPG Key ID",
    "gpg",
    "337D163DC4067320DB87C42BF5D2A41B6E7774A4",
    ""
  ),
  Credentials(
    "Sonatype Nexus Repository Manager",
    "s01.oss.sonatype.org",
    "luigi-antognini",
    Option(System.getenv("SONATYPE_PASSWORD")).getOrElse("")
  )
)
ThisBuild / publishTo := sonatypePublishToBundle.value
