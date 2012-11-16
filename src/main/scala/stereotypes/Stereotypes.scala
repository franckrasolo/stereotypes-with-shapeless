package stereotypes

case class Stereotype(name: String, description: String, aliases: List[String])

trait Stereotypes {
  import shapeless._, Searchable._

  val stereotypes: List[Stereotype]
  private implicit def stereotypeIso = Iso.hlist(Stereotype.apply _, Stereotype.unapply _)

  def find(term: String): Either[String, Stereotype] = stereotypes
    .deepFind((_: Stereotype).deepFind((_: String) equalsIgnoreCase term).isDefined)
    .toRight(message(term))

  private def message(term: String) = "Invalid Stereotype [%s]. Must be one of [%s]".format(term, allowedTerms)
  private lazy val allowedTerms = stereotypes.flatMap(s => s.name :: s.description :: s.aliases).sorted.mkString(", ")
}
