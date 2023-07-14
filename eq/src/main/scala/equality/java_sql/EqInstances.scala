package equality.java_sql

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_sql_SQLType: Eq[java.sql.SQLType] = Eq.assumed
