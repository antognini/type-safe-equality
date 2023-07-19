package equality.scala_util

import equality.core.Eq

export Instances.given

object Instances:
  given scala_util_Either[A: Eq, B: Eq]: Eq[scala.util.Either[A, B]] = Eq.assumed
  given scala_util_Try[T: Eq]: Eq[scala.util.Try[T]] = Eq.assumed
