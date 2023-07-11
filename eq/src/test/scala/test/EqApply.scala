package test

import equality.all.{*, given}

import java.time.LocalDate
import java.time.temporal.ChronoField

enum Century derives Eq:
  case C1800, C1900, C2000


case class PersonId(x: Int) derives Eq
case class Person(id: PersonId,
                  name: String,
                  dateOfBirth: LocalDate) derives Eq:

  def centuryOfBirth: Century = Eq:
    dateOfBirth.get(ChronoField.YEAR) match
      case year if year <= 1800 && year < 1900 => Century.C1800
      case year if year <= 1900 && year < 2000 => Century.C1900
      case year if year <= 2000 && year < 2100 => Century.C2000