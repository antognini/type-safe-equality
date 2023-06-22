package equality.java_text

import equality.Eq

export EqInstances.given

private[equality] object EqInstances:
  given java_text_Collator: Eq[java.text.Collator] = Eq
  given java_text_NumberFormat: Eq[java.text.NumberFormat] = Eq
