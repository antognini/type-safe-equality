package equality.java_text

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given java_text_Collator: Eq[java.text.Collator] = Eq.assumed
  given java_text_NumberFormat: Eq[java.text.NumberFormat] = Eq.assumed
