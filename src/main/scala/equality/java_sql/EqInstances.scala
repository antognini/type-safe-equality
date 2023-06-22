package equality.java_sql

import equality.Eq

export EqInstances.given

private[equality] object EqInstances:
  given java_sql_Date: Eq[java.sql.Date] = Eq
  given java_sql_Time: Eq[java.sql.Time] = Eq
  given java_sql_Timestamp: Eq[java.sql.Timestamp] = Eq
  given java_sql_SQLType: Eq[java.sql.SQLType] = Eq
