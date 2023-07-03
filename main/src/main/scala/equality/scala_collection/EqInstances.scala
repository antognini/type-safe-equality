package equality.scala_collection

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given scala_collection_Seq[A: Eq]: Eq[scala.collection.Seq[A]] = Eq.assumed
  given scala_collection_Set[A: Eq]: Eq[scala.collection.Set[A]] = Eq.assumed
  given scala_collection_Map[A: Eq, B: Eq]: Eq[scala.collection.Map[A, B]] = Eq.assumed
