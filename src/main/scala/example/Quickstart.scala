package example

import scala.annotation.nowarn

@main def quickStart() =
  eqTypeClass
  standardEqInstances
  collectionExtensions

@nowarn
def eqTypeClass: Unit =
  import equality.Eq
  import java.util.jar.Attributes

  // making jar attributes comparable with != and ++ with strict equality enabled
  given Eq[Attributes] = Eq

  Attributes() == Attributes()

@nowarn
def standardEqInstances: Unit =
  import equality.given
  import java.time.{LocalDate, LocalDateTime}

  val now = LocalDateTime.now
  val later = LocalDateTime.now
  now == later

  val today = LocalDate.now

  // today == now
  // ERROR: Values of types java.time.LocalDate and java.time.LocalDateTime cannot be compared with == or !=


def collectionExtensions: Unit =
  import equality.collection.*

  val names = List( "Alice", "Bob")
  names.contains_safe("Peter") // equality-safe alternative to .contains()

  // names.contains_safe(1)
  // ERROR: Values of types A and A cannot be compared with == or !=
  // where: A is a type variable with constraint >: String | Int