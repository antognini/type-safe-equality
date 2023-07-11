package equality.java_util

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_util_Date: Eq[java.util.Date] = Eq.assumed
  given java_util_Locale: Eq[java.util.Locale] = Eq.assumed
  given java_util_Locale_Category: Eq[java.util.Locale.Category] = Eq.assumed
  given java_util_Locale_IsoCountryCode: Eq[java.util.Locale.IsoCountryCode] = Eq.assumed
  given java_util_Locale_LanguageRange: Eq[java.util.Locale.LanguageRange] = Eq.assumed
  given java_util_Properties: Eq[java.util.Properties] = Eq.assumed
  given java_util_TimeZone: Eq[java.util.TimeZone] = Eq.assumed

  given java_util_Optional[T: Eq]: Eq[java.util.Optional[T]] = Eq.assumed

  given java_util_List[E: Eq]: Eq[java.util.List[E]] = Eq.assumed
  given java_util_Queue[E: Eq]: Eq[java.util.Queue[E]] = Eq.assumed
  given java_util_Set[E: Eq]: Eq[java.util.Set[E]] = Eq.assumed
  given java_util_Map[K: Eq, V: Eq]: Eq[java.util.Map[K, V]] = Eq.assumed
