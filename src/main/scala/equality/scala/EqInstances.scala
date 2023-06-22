package equality.scala

import equality.Eq

export EqInstances.given

private[equality] object EqInstances:
  given scala_Array[T]: Eq[scala.Array[T]] = Eq
