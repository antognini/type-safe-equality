package equality.java_math

import equality.Eq

export EqInstances.given

private[equality] object EqInstances:
    given java_math_MathContext: Eq[java.math.MathContext] = Eq
    given java_math_RoundingMode: Eq[java.math.RoundingMode] = Eq
