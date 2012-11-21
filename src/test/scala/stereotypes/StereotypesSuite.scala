package stereotypes

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StereotypesSuite extends FunSuite {

  trait Examples extends Stereotypes {
    val australia  = Stereotype("australia",  "a person from australia",   List("aussie", "legend"))
    val newZealand = Stereotype("newzealand", "a person from new zealand", List("kiwi"))
    val preston    = Stereotype("preston",    "a person from preston",     List("scally"))
    val liverpool  = Stereotype("liverpool",  "a person from liverpool",   List("scouser", "thief"))
    val manchester = Stereotype("manchester", "a person from manchester",  List("manc", "mancunian", "fighter"))

    val stereotypes = List(australia, newZealand, preston, liverpool, manchester)
    def find(term: String) = find(_ equalsIgnoreCase term)(term)
  }

  test("find by description") {
    new Examples {
      assert(find("a person from new zealand") === Right(newZealand))
    }
  }

  test("find by name") {
    new Examples {
      assert(find("manchester") === Right(manchester))
    }
  }

  test("find by alias") {
    new Examples {
      assert(find("scouser") === Right(liverpool))
    }
  }

  test("find is case insensitive") {
    new Examples {
      assert(find("scally") === Right(preston))
      assert(find("SCALLY") === Right(preston))
      assert(find("ScAlLy") === Right(preston))
    }
  }

  test("find returns a descriptive message when no stereotype is found") {
    new Examples {
      assert(find("Awesome") === Left(
        "Invalid Stereotype [Awesome]. Must be one of [a person from australia, " +
          "a person from liverpool, a person from manchester, a person from new zealand, " +
          "a person from preston, aussie, australia, fighter, kiwi, legend, liverpool, " +
          "manc, manchester, mancunian, newzealand, preston, scally, scouser, thief]"
      ))
    }
  }
}
