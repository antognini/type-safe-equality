package equality.java_text

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_text_Collator: Eq[java.text.Collator] = Eq.assumed
  given java_text_Format: Eq[java.text.Format] = Eq.assumed
