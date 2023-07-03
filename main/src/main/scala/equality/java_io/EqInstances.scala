package equality.java_io

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given java_io_File: Eq[java.io.File] = Eq.assumed
