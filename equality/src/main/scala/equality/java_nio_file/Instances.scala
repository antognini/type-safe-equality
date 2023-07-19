package equality.java_nio_file

import equality.core.Eq

export Instances.given

object Instances:
  given java_nio_file_Path: Eq[java.nio.file.Path] = Eq.assumed
