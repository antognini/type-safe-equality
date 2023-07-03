package equality.scala_all

import equality.Eq

export EqInstances.given

object EqInstances:
  export equality.scala_.EqInstances.given
  export equality.scala_.EqInstances.{AnyNumber, AnyJavaNumber}
  export equality.scala_collection.EqInstances.given
  export equality.scala_concurrent_duration.EqInstances.given
  export equality.scala_io.EqInstances.given
  export equality.scala_math.EqInstances.given
  export equality.scala_util.EqInstances.given
