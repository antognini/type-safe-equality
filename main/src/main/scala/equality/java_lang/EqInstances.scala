package equality.java_lang

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:

  given java_lang_Void: Eq[java.lang.Void] = Eq.assumed

  given java_lang_Number: Eq[java.lang.Number] = Eq.assumed

  given java_lang_Byte: Eq[java.lang.Byte] = Eq.assumed
  given java_lang_Short: Eq[java.lang.Short] = Eq.assumed
  given java_lang_Integer: Eq[java.lang.Integer] = Eq.assumed
  given java_lang_Long: Eq[java.lang.Long] = Eq.assumed

  given java_lang_Float: Eq[java.lang.Float] = Eq.assumed
  given java_lang_Double: Eq[java.lang.Double] = Eq.assumed

  given java_lang_StringBuilder: Eq[java.lang.StringBuilder] = Eq.assumed
  given java_lang_Character: Eq[java.lang.Character] = Eq.assumed

  given java_lang_Boolean: Eq[java.lang.Boolean] = Eq.assumed
