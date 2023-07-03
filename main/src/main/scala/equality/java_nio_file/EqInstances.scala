package equality.java_nio_file

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given java_nio_file_Path: Eq[java.nio.file.Path] = Eq.assumed
