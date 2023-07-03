package equality.scala_math

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given scala_math_BigDecimal: Eq[scala.math.BigDecimal] = Eq.assumed
  given scala_math_BigInt: Eq[scala.math.BigInt] = Eq.assumed
