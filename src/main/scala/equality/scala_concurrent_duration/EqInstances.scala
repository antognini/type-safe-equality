package equality.scala_concurrent_duration

import equality.Eq

export EqInstances.given

private[equality] object EqInstances:
  given scala_concurrent_duration_Duration: Eq[scala.concurrent.duration.Duration] = Eq
  given scala_concurrent_duration_Deadline: Eq[scala.concurrent.duration.Deadline] = Eq
