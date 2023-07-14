package equality.java_lang

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_lang_Boolean: Eq[java.lang.Boolean] = Eq.assumed
  given java_lang_Byte: Eq[java.lang.Byte] = Eq.assumed
  given java_lang_Character: Eq[java.lang.Character] = Eq.assumed
  given java_lang_Double: Eq[java.lang.Double] = Eq.assumed
  given java_lang_Enum[A <: java.lang.Enum[A]]: Eq[A] = Eq.assumed
  given java_lang_Float: Eq[java.lang.Float] = Eq.assumed
  given java_lang_Integer: Eq[java.lang.Integer] = Eq.assumed
  given java_lang_Long: Eq[java.lang.Long] = Eq.assumed
  given java_lang_Number: Eq[java.lang.Number] = Eq.assumed
  given java_lang_Short: Eq[java.lang.Short] = Eq.assumed
  given java_lang_Thread: Eq[java.lang.Thread] = Eq.assumed
  given java_lang_Throwable: Eq[java.lang.Throwable] = Eq.assumed
  given java_lang_Void: Eq[java.lang.Void] = Eq.assumed
