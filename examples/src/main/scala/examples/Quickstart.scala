package examples

import scala.annotation.nowarn

@main def quickStart():Unit =
  getting_started
  verified_equality_for_composed_case_classes_via_type_class_derivation
  verified_equality_for_composed_case_classes_with_type_parameters_via_type_class_derivation
  verified_equality_for_an_existing_arbitrary_class_with_a_given
  assumed_equality_for_the_bottom_classes_of_a_class_hierarchy_via_type_class_derivation
  assumed_equality_for_the_base_class_of_a_class_hierarchy_via_type_class_derivation
  assumed_equality_for_an_existing_arbitrary_class_with_a_given
  standardEqInstances
  collectionExtension
  strict_equality_opt_out_for_an_entire_compilation_unit
  strict_equality_opt_out_for_a_local_scope
  numberEquality
  enums


def getting_started: Unit =
  import java.time.LocalDateTime
  import java.util.jar.Attributes

  // Use equality for a standard library type out of the box
  val now = LocalDateTime.now
  now == now

  // Explicitly assume equality for an arbitrary type
  given Eq[Attributes] = Eq.assumed
  Attributes() == Attributes()

  // Derive equality for a product type
  case class Box[A: Eq](
    name: String,
    item: Either[String, A]
  ) derives Eq
  val box = Box("my box", Right(Attributes()))
  box == box

  // Use an equality-safe alternative to .contains()
  val names = List(now, now)
  names.contains_eq(now)


@nowarn
def verified_equality_for_composed_case_classes_via_type_class_derivation: Unit =

  case class Email( address:String) derives Eq

  // Only compiles because class Email derives Eq
  case class Person(
    name: String,
    contact: Email,
  ) derives Eq

  val person = Person("Alice", Email("alice@maluma.osw"))

  // Only compiles because class Person derives Eq
  person == person


@nowarn
def verified_equality_for_composed_case_classes_with_type_parameters_via_type_class_derivation: Unit =

  case class Email( address:String) derives Eq

  // Only compiles because the type parameter A is declared with a context bound [A: Eq]
  case class Person[A: Eq](
    name: String,
    contact: A,
  ) derives Eq

  // Only compiles because class Email derives Eq
  val person = Person("Alice", Email("alice@maluma.osw"))

  // Only compiles because class Person derives Eq
  person == person

case class SomeProduct()
@nowarn
def verified_equality_for_an_existing_arbitrary_class_with_a_given: Unit =

  // Only compiles because SomeProduct conforms to the equality rules
  given Eq[SomeProduct] = Eq.derived

  // Only compiles with the given instance above
  SomeProduct() == SomeProduct()


@nowarn
def assumed_equality_for_the_bottom_classes_of_a_class_hierarchy_via_type_class_derivation: Unit =

  class Animal
  case class Cat() extends Animal derives Eq.assumed
  case class Dog() extends Animal derives Eq.assumed

  // Values of type Cat can compare each other with == or !=
  // Values of type Dog can compare each other with == or !=
  // Within this hierarchy, any other comparison with == or != fails


@nowarn
def assumed_equality_for_the_base_class_of_a_class_hierarchy_via_type_class_derivation: Unit =

  class Animal derives Eq.assumed
  case class Cat() extends Animal
  case class Dog() extends Animal

  // Within this hierarchy, any value can compare to any other value with == or !=


@nowarn
def assumed_equality_for_an_existing_arbitrary_class_with_a_given: Unit =

  import java.util.jar.Attributes

  // Always compiles
  given Eq[Attributes] = Eq.assumed

  // Only compiles with the given instance above
  Attributes() == Attributes()


@nowarn
def standardEqInstances: Unit =

  import java.time.{LocalDate, LocalDateTime}

  val now = LocalDateTime.now
  val later = LocalDateTime.now

  // Compiles out of the box
  now == later

  val today = LocalDate.now

  // today == now
  // ERROR: Values of types LocalDate and LocalDateTime cannot be compared with == or !=


@nowarn
def collectionExtension:Unit =

  case class Apple(x: String) derives Eq
  val appleA = Apple("A")
  val appleB = Apple("B")
  val appleC = Apple("C")
  val apples = List(appleA, appleB, appleC)

  case class Car(x: String) derives Eq
  val carY = Car("Y")
  val carX = Car("X")
  val cars = List(carX, carY)

  // It's pointless to search for a car in a list of apples
  apples.contains(carX)
  // Type checks but it shouldn't --> yields false

  // apples.contains_eq(carX)
  // ERROR: Values of types A and A cannot be compared with == or !=
  //        where: A is a type variable with constraint >: Apple | Car

  // It is pointless to remove a list of cars from a list of apples
  apples.diff(cars)
  // Type checks but it shouldn't --> returns the original list

  // apples.diff_eq(cars)
  // ERROR: Values of types Apple and Apple | Car cannot be compared with == or !=


@nowarn
def strict_equality_opt_out_for_an_entire_compilation_unit: Unit =
  // Import to disable strict equality
  import equality.universal.given

  // Only compiles because strict equality is disabled
  val _ = 1 == "abc"


@nowarn
def strict_equality_opt_out_for_a_local_scope: Unit =
  def areEqual(x1: Any, x2: Any): Boolean =
    // Import to locally disable strict equality
    import equality.universal.given

    // Only compiles because strict equality is disabled
    x1 == x2


def numberEquality: Unit =

  // Covariant type parameter A
  case class Box[+A: Eq](a: A) derives Eq

  val box1 = Box(Seq(Some( (true, 'a', 1  ) )))
  val box2 = Box(Seq(Some( (true, 'a', 1L ) )))

  // Compiles because type parameter A is covariant and an instance of
  // Eq[ Box[Seq[Option[ (Boolean, Char, AnyNumber) ]]] ]
  // is available and can be applied both to Eq[box1.type] and Eq[box2.type].
  box1 == box2


@nowarn
def enums:Unit =

  enum Weekday derives Eq:
    // These are instances of the product type Weekday
    case Monday, Tuesday, Wednesday // ...

  // Only compiles because enum Weekday derives Eq
  val myDay: Weekday = Weekday.Monday
  myDay == myDay
