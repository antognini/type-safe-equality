package equality.scala_concurrent_duration

import equality.core.Eq

export Instances.given

object Instances:
  given scala_concurrent_duration_Deadline: Eq[scala.concurrent.duration.Deadline] = Eq.assumed
  given scala_concurrent_duration_Duration: Eq[scala.concurrent.duration.Duration] = Eq.assumed
