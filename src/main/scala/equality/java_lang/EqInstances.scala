package equality.java_lang

import equality.Eq

export EqInstances.given
object EqInstances:
  given java_lang_Enum: Eq[java.lang.Enum[?]] = Eq
  given java_lang_Boolean: Eq[java.lang.Boolean] = Eq
  given java_lang_Throwable: Eq[java.lang.Throwable] = Eq
