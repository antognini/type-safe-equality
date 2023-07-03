package equality.java_sql

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given java_sql_Date: Eq[java.sql.Date] = Eq.assumed
  given java_sql_Time: Eq[java.sql.Time] = Eq.assumed
  given java_sql_Timestamp: Eq[java.sql.Timestamp] = Eq.assumed
  given java_sql_SQLType: Eq[java.sql.SQLType] = Eq.assumed
