package equality.java_sql

import equality.core.Eq

export Instances.given

object Instances:
  given java_sql_SQLType: Eq[java.sql.SQLType] = Eq.assumed
