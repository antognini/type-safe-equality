package equality.java_io

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_io_File: Eq[java.io.File] = Eq
