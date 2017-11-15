enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)

name := "retraction-viz"

version := "0.1"

scalaVersion := "2.12.4"

scalaJSUseMainModuleInitializer := true

resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.3"
libraryDependencies += "com.github.fdietze" % "scala-js-d3v4" % "master-SNAPSHOT"
libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.6.7"
