package equality.scala

import equality.Eq

export EqInstances.given

object EqInstances:
  given scala_Array[T]: Eq[scala.Array[T]] = Eq
