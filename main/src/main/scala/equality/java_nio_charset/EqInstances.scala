package equality.java_nio_charset

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given java_nio_charset_CharSet: Eq[java.nio.charset.Charset] = Eq.assumed
