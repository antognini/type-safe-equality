package equality.scala_io

import equality.Eq

export EqInstances.given

object EqInstances:
  given scala_io_Codec: Eq[scala.io.Codec] = Eq.assumed
