package stereotypes

case class Stereotype(name: String, description: String, aliases: List[String])

trait Stereotypes {
  import shapeless._, Searchable._

  def find(term: String): Either[String, Stereotype]
  val stereotypes: List[Stereotype]

  private implicit def stereotypeIso = Iso.hlist(Stereotype.apply _, Stereotype.unapply _)
  protected def find(predicate: String => Boolean)(term: String) =
    stereotypes.find(_.deepFind(predicate).isDefined).toRight(message(term))

  private def message(term: String) = s"Invalid Stereotype [$term]. Must be one of [$allowedTerms]"
  private lazy val allowedTerms = stereotypes.flatMap(s => s.name :: s.description :: s.aliases).sorted.mkString(", ")
}
