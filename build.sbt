name := "stereotypes-with-shapeless"

scalaVersion := "2.10.0-RC2"

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype OSS Public Repositories" at "https://oss.sonatype.org/content/groups/public/"
)
 
libraryDependencies ++= Seq(
  "com.chuusai" % "shapeless" % "1.2.3-SNAPSHOT" cross CrossVersion.full withSources(),
  "org.scalatest" % "scalatest" % "1.8" % "test" cross CrossVersion.full withSources(),
  "junit" % "junit" % "4.11" % "test"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions")

initialCommands in console := "import shapeless._"
