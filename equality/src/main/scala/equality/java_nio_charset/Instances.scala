package equality.java_nio_charset

import equality.core.Eq

export Instances.given

object Instances:
  given java_nio_charset_CharSet: Eq[java.nio.charset.Charset] = Eq.assumed
