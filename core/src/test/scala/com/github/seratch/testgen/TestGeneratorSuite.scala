package com.github.seratch.testgen

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestGeneratorSuite extends FunSuite with ShouldMatchers {

  type ? = this.type

  val extractor = new TargetExtractor
  val generator = new TestGenerator

  test("generate test to no arg target") {
    val targets = extractor.extract("src/test/scala/com/example/noargs.scala")
    targets foreach {
      case target => {
        val test = generator.generate(target)
        test should not be null
      }
    }
  }

}