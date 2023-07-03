package equality.java_time

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given java_time_Duration: Eq[java.time.Duration] = Eq.assumed
  given java_time_Instant: Eq[java.time.Instant] = Eq.assumed
  given java_time_LocalDate: Eq[java.time.LocalDate] = Eq.assumed
  given java_time_LocalDateTime: Eq[java.time.LocalDateTime] = Eq.assumed
  given java_time_LocalTime: Eq[java.time.LocalTime] = Eq.assumed
  given java_time_MonthDay: Eq[java.time.MonthDay] = Eq.assumed
  given java_time_OffsetDateTime: Eq[java.time.OffsetDateTime] = Eq.assumed
  given java_time_OffsetTime: Eq[java.time.OffsetTime] = Eq.assumed
  given java_time_Period: Eq[java.time.Period] = Eq.assumed
  given java_time_Year: Eq[java.time.Year] = Eq.assumed
  given java_time_YearMonth: Eq[java.time.YearMonth] = Eq.assumed
  given java_time_ZonedDateTime: Eq[java.time.ZonedDateTime] = Eq.assumed
  given java_time_ZoneId: Eq[java.time.ZoneId] = Eq.assumed
  given java_time_ZoneOffset: Eq[java.time.ZoneOffset] = Eq.assumed
  given java_time_DayOfWeek: Eq[java.time.DayOfWeek] = Eq.assumed
  given java_time_Month: Eq[java.time.Month] = Eq.assumed
