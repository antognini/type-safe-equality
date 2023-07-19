package equality.scala_concurrent

import equality.core.{Eq, EqRef}

export Instances.given

object Instances:
  given scala_concurrent_Future[T: Eq]: EqRef[scala.concurrent.Future[T]] = EqRef.assumed
  given scala_concurrent_Promise[T: Eq]: EqRef[scala.concurrent.Promise[T]] = EqRef.assumed
