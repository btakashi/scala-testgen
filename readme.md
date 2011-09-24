# testgen sbt-plugin

"testgen" is a Scala unit test code generator.

## What's this?

This is the sbt plugin for "testgen".

## How to use?

### prepare xsbt 0.10.1

    https://github.com/harrah/xsbt/wiki/Setup

### mkdir -p {root}/project/plugins

if not exist.

### vim {root}/project/plugins/testgen.sbt

    // *** Put this under project/plugins/ ***

    scalaVersion := "2.8.1"

    resolvers ++= Seq(
      "seratch.github.com releases"  at "http://seratch.github.com/mvn-repo/releases",
      "seratch.github.com snapshots" at "http://seratch.github.com/mvn-repo/snapshots"
    )

    libraryDependencies ++= Seq(
      "com.github.seratch" %% "testgen-core" % "0.1-SNAPSHOT",
      "com.github.seratch" %% "testgen-sbt" % "0.1-SNAPSHOT"
    )

### run sbt and "testgen" command

    sbt
    > testgen src/main/scala/com/example/MyApp.scala
    > MyAppSuite is created.

### then Happy testing! :)

Have fun!

