package equality.java_security

import equality.Eq

export EqInstances.given

object EqInstances:
  given scala_security_Permission: Eq[java.security.Permission] = Eq.assumed
  given scala_security_Principal: Eq[java.security.Principal] = Eq.assumed
  given scala_security_Timestamp: Eq[java.security.Timestamp] = Eq.assumed
