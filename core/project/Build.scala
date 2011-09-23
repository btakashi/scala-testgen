import java.io.File
import sbt._
import Keys._

// imports standard command parsing functionality

import complete.DefaultParsers._

object CommandExample extends Build {
  // Declare a single project, adding several new commands, which are discussed below.
  lazy override val projects = Seq(root)
  lazy val root = Project("root", file(".")) settings (
    commands ++= Seq(hello, helloAll, failIfTrue, changeColor, printState)
    )

  // A simple, no-argument command that prints "Hi",
  //  leaving the current state unchanged.
  def hello = Command.command("hello") {
    state => {
      val f = new File(".")
      println(f.getAbsolutePath)
      println("Hi!")
      state
    }
  }


  // A simple, multiple-argument command that prints "Hi" followed by the arguments.
  //   Again, it leaves the current state unchanged.
  def helloAll = Command.args("hello-all", "<name>") {
    (state, args) =>
      println("Hi " + args.mkString(" "))
      state
  }


  // A command that demonstrates failing or succeeding based on the input
  def failIfTrue = Command.single("fail-if-true") {
    case (state, "true") => state.fail
    case (state, _) => state
  }


  // Demonstration of a custom parser.
  // The command changes the foreground or background terminal color
  //  according to the input.
  lazy val change = Space ~> (reset | setColor)
  lazy val reset = token("reset" ^^^ "\\033[0m")
  lazy val color = token(Space ~> ("blue" ^^^ "4" | "green" ^^^ "2"))
  lazy val select = token("fg" ^^^ "3" | "bg" ^^^ "4")
  lazy val setColor = (select ~ color) map {
    case (g, c) => "\\033[" + g + c + "m"
  }

  def changeColor = Command("color")(_ => change) {
    (state, ansicode) =>
      print(ansicode)
      state
  }


  // A command that demonstrates getting information out of State.
  def printState = Command.command("print-state") {
    state =>
      import state._
      println(definedCommands.size + " registered commands")
      println("commands to run: " + show(remainingCommands))
      println()

      println("original arguments: " + show(configuration.arguments))
      println("base directory: " + configuration.baseDirectory)
      println()

      println("sbt version: " + configuration.provider.id.version)
      println("Scala version (for sbt): " + configuration.provider.scalaProvider.version)
      println()

      val extracted = Project.extract(state)
      import extracted._
      println("Current build: " + currentRef.build)
      println("Current project: " + currentRef.project)
      println("Original setting count: " + session.original.size)
      println("Session setting count: " + session.append.size)

      state
  }

  def show[T](s: Seq[T]) =
    s.map("'" + _ + "'").mkString("[", ", ", "]")
}