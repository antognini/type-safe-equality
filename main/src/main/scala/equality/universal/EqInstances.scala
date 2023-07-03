package equality.universal

import equality.Eq

export equality.scala_.EqInstances.scala_CanEqual
export EqInstances.given

object EqInstances:
  given scala_Any: Eq[Any] = Eq.assumed

