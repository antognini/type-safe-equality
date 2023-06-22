// Project
ThisBuild / organization := "ch.produs"
ThisBuild / organizationName := "produs ag"
ThisBuild / organizationHomepage := None
ThisBuild / description := "Scala 3 type safe equality"
ThisBuild / homepage := Some(url("https://github.com/antognini/type-safe-equality"))
ThisBuild / licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / developers := List(
    Developer(
      id = "LA",
      name = "Luigi Antognini",
      email = "",
      url = url("https://github.com/antognini")
    )
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "type-safe-equality",
    version := "0.0.0-SNAPSHOT",
    scalaVersion := "3.3.0",
    Compile / scalacOptions ++= Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-language:scala3",
      "-new-syntax",
      "-indent",
      "-language:strictEquality",
      "-java-output-version", "11",
      "-Wunused:all"
    )
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
ThisBuild / versionScheme := Some("semver-spec")