package equality.scala_math

import equality.core.Eq

export Instances.given

object Instances:
  given scala_math_BigDecimal: Eq[scala.math.BigDecimal] = Eq.assumed
  given scala_math_BigInt: Eq[scala.math.BigInt] = Eq.assumed
