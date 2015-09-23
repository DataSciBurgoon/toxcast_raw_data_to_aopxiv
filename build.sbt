val json4sNative = "org.json4s" %% "json4s-native" % "3.2.11"
val scalaTest = "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

lazy val root = (project in file(".")).
	settings(
		name := "toxcast raw data parser",
		version := "1.0",
		scalaVersion := "2.11.6",
		libraryDependencies += json4sNative,
		libraryDependencies += scalaTest
	)

