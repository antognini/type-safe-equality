package equality.scala_io

import equality.core.Eq

export Instances.given

object Instances:
  given scala_io_Codec: Eq[scala.io.Codec] = Eq.assumed
