package equality.scala_concurrent

import equality.Eq

export EqInstances.given

object EqInstances:
  given scala_concurrent_Future[T: Eq]: Eq[scala.concurrent.Future[T]] = Eq.assumed
  given scala_concurrent_Promise[T: Eq]: Eq[scala.concurrent.Promise[T]] = Eq.assumed
