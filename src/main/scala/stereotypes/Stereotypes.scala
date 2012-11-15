package stereotypes

case class Stereotype(name: String, description: String, aliases: List[String])

trait Stereotypes {
  import shapeless._, Searchable._

  private implicit def stereotypeIso = Iso.hlist(Stereotype.apply _, Stereotype.unapply _)

  val stereotypes: List[Stereotype]

  def find(term: String): Either[String, Stereotype] = stereotypes
    .deepFind((_: Stereotype).deepFind((_: String) equalsIgnoreCase term).isDefined)
    .toRight(message(term))

  private def message(term: String) = "Invalid Stereotype [%s]. Must be one of [%s]".format(term, searchableTerms)
  private lazy val searchableTerms = stereotypes.flatMap(s => s.name :: s.description :: s.aliases).sorted.mkString(", ")
}
