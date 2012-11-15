package shapeless

trait Searchable[A, Q] {
  def find(p: Q => Boolean)(a: A): Option[Q]
}

object Searchable {

  implicit def elemSearchable[A] =
    new Searchable[A, A] {
      def find(p: A => Boolean)(a: A) = if (p(a)) Some(a) else None
    }

  implicit def listSearchable[A, Q](implicit s: Searchable[A, Q]) =
    new Searchable[List[A], Q] {
      def find(p: Q => Boolean)(a: List[A]) = a.flatMap(s.find(p)).headOption
    }

  implicit def hnilSearchable[Q] = new Searchable[HNil, Q] {
    def find(p: Q => Boolean)(a: HNil) = None
  }

  implicit def hlistSearchable[H, T <: HList, Q](implicit hs: Searchable[H, Q] = null, ts: Searchable[T, Q]) =
    new Searchable[H :: T, Q] {
      def find(p: Q => Boolean)(a: H :: T) = Option(hs).flatMap(_.find(p)(a.head)) orElse ts.find(p)(a.tail)
    }

  implicit def hlistishSearchable[A, L <: HList, Q](implicit iso: Iso[A, L], s: Searchable[L, Q]) =
    new Searchable[A, Q] {
      def find(p: Q => Boolean)(a: A) = s.find(p)(iso to a)
    }


  case class SearchableWrapper[A](a: A) {
    def deepFind[Q](p: Q => Boolean)(implicit s: Searchable[A, Q]) = s.find(p)(a)
  }

  implicit def wrapSearchable[A](a: A) = SearchableWrapper(a)
}
