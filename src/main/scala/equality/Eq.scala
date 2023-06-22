package equality

import util.NotGiven

export equality.java_io.EqInstances.given
export equality.java_lang.EqInstances.given
export equality.java_math.EqInstances.given
export equality.java_net.EqInstances.given
export equality.java_nio.EqInstances.given
export equality.java_nio_charset.EqInstances.given
export equality.java_nio_file.EqInstances.given
export equality.java_sql.EqInstances.given
export equality.java_text.EqInstances.given
export equality.java_time.EqInstances.given
export equality.java_util.EqInstances.given
export equality.scala.EqInstances.given
export equality.scala_concurrent_duration.EqInstances.given

type Eq[-T] = CanEqual[T, T]
val Eq = CanEqual.derived

def enforceStrictEquality()(using NotGiven[CanEqual[Any, Any]]): Boolean = true
private val enforce = enforceStrictEquality()