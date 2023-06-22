package equality.java_nio_charset

import equality.Eq

export EqInstances.given

private[equality] object EqInstances:
  given java_nio_charset_CharSet: Eq[java.nio.charset.Charset] = Eq
