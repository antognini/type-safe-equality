package equality.java_security

import equality.core.{Eq, EqRef}

export Instances.given

object Instances:
  given java_security_Permission: Eq[java.security.Permission] = Eq.assumed
  given java_security_Principal: Eq[java.security.Principal] = Eq.assumed
  given java_security_Timestamp: Eq[java.security.Timestamp] = Eq.assumed
