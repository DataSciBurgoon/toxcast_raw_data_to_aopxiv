val json4sNative = "org.json4s" %% "json4s-native" % "3.2.11"

lazy val root = (project in file(".")).
	settings(
		name := "toxcast raw data parser",
		version := "1.0",
		scalaVersion := "2.11.6",
		libraryDependencies += json4sNative
	)

