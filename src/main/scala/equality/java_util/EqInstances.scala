package equality.java_util

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_util_Date: Eq[java.util.Date] = Eq
  given java_util_Locale: Eq[java.util.Locale] = Eq
  given java_util_Locale_Category: Eq[java.util.Locale.Category] = Eq
  given java_util_Locale_IsoCountryCode: Eq[java.util.Locale.IsoCountryCode] = Eq
  given java_util_Locale_LanguageRange: Eq[java.util.Locale.LanguageRange] = Eq
  given java_util_Properties: Eq[java.util.Properties] = Eq
  given java_util_TimeZone: Eq[java.util.TimeZone] = Eq
