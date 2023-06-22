package equality.java_time

import equality.Eq

export EqInstances.given
object EqInstances:
  given java_time_Duration: Eq[java.time.Duration] = Eq
  given java_time_Instant: Eq[java.time.Instant] = Eq
  given java_time_LocalDate: Eq[java.time.LocalDate] = Eq
  given java_time_LocalDateTime: Eq[java.time.LocalDateTime] = Eq
  given java_time_LocalTime: Eq[java.time.LocalTime] = Eq
  given java_time_MonthDay: Eq[java.time.MonthDay] = Eq
  given java_time_OffsetDateTime: Eq[java.time.OffsetDateTime] = Eq
  given java_time_OffsetTime: Eq[java.time.OffsetTime] = Eq
  given java_time_Period: Eq[java.time.Period] = Eq
  given java_time_Year: Eq[java.time.Year] = Eq
  given java_time_YearMonth: Eq[java.time.YearMonth] = Eq
  given java_time_ZonedDateTime: Eq[java.time.ZonedDateTime] = Eq
  given java_time_ZoneId: Eq[java.time.ZoneId] = Eq
  given java_time_ZoneOffset: Eq[java.time.ZoneOffset] = Eq
  given java_time_DayOfWeek: Eq[java.time.DayOfWeek] = Eq
  given java_time_Month: Eq[java.time.Month] = Eq
