package com.example

import org.scalatest._
import org.scalatest.matchers._

class MyTrait1Suite extends FunSuite with ShouldMatchers {

  type ? = this.type

  test("available") {
    val mixined = new Object with MyTrait1
    mixined should not be null
  }

}
