package stereotypes

import org.junit.runner.RunWith
import org.scalatest.{EitherValues, FunSuite}
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.MustMatchers

@RunWith(classOf[JUnitRunner])
class StereotypesSuite extends FunSuite {

  trait Examples extends Stereotypes with MustMatchers with EitherValues {
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
      find("a person from new zealand").right.value must be === newZealand
    }
  }

  test("find by name") {
    new Examples {
      find("manchester").right.value must be === manchester
    }
  }

  test("find by alias") {
    new Examples {
      find("scouser").right.value must be === liverpool
    }
  }

  test("find is case insensitive") {
    new Examples {
      find("scally").right.value must be === preston
      find("SCALLY").right.value must be === preston
      find("ScAlLy").right.value must be === preston
    }
  }

  test("find returns a descriptive message when no stereotype is found") {
    new Examples {
      find("Awesome").left.value must be === "Invalid Stereotype [Awesome]. Must be one of [a person from australia, " +
        "a person from liverpool, a person from manchester, a person from new zealand, " +
        "a person from preston, aussie, australia, fighter, kiwi, legend, liverpool, " +
        "manc, manchester, mancunian, newzealand, preston, scally, scouser, thief]"
    }
  }
}
