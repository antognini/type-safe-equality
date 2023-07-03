package equality.scala_concurrent_duration

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given scala_concurrent_duration_Duration: Eq[scala.concurrent.duration.Duration] = Eq.assumed
  given scala_concurrent_duration_Deadline: Eq[scala.concurrent.duration.Deadline] = Eq.assumed
