package equality.universal

import equality.Eq

export EqInstances.given

object EqInstances:
  given scala_Any: Eq[Any] = Eq.assumed

