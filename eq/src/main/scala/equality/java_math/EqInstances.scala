package equality.java_math

import equality.Eq

export EqInstances.given

object EqInstances:
  //    java_math_BigDecimal is intentionally omitted because it has a bug in equals()
  given java_math_BigInteger: Eq[java.math.BigInteger] = Eq.assumed
  given java_math_MathContext: Eq[java.math.MathContext] = Eq.assumed
  given java_math_RoundingMode: Eq[java.math.RoundingMode] = Eq.assumed
