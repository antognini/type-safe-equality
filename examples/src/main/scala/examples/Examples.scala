package examples


@main def examples(): Unit =
  quickstart
  value_derives_verified_case_class
  value_derives_verified_parametrized_case_class
  value_given_verified_enum
  value_derives_assumed_bottom_class_hierarchy
  value_derives_assumed_base_class
  value_given_assumed_arbitrary_type
  reference_derives_verified
  reference_derives_assumed
  reference_given_verified
  reference_given_assumed
  standard_instances
  collection_extensions
  universal_equality
  number_equality


def quickstart: Unit =
  import java.time.LocalDateTime
  import java.util.jar.Attributes

  // Use equality for a standard library type out of the box
  val now = LocalDateTime.now
  now == now

  // Explicitly assume equality for an arbitrary type
  given Eq[Attributes] = Eq.assumed
  Attributes() == Attributes()

  // Derive equality for a product type
  case class Box[T: Eq](
    name: String,
    item: Either[String, T]
  ) derives Eq
  val box = Box("", Right(Attributes()))
  box == box

  // Use an equality-safe alternative to .contains()
  val names = List(now, now)
  names.contains_eq(now)


def value_derives_verified_case_class: Unit =
  case class Email(address: String) derives Eq

  // Compiles because Email derives Eq
  case class Person(
    name: String,
    contact: Email,
  ) derives Eq

  val person = Person("Alice", Email("alice@example.net"))

  // Compiles because Person derives Eq
  person == person


def value_derives_verified_parametrized_case_class: Unit =
  case class Email(address: String) derives Eq

  // Compiles because the type parameter T is declared with a context bound [T: Eq]
  case class Person[T: Eq](
    name: String,
    contact: T,
  ) derives Eq

  // Compiles because Email derives Eq
  val person = Person("Alice", Email("alice@example.net"))

  // Compiles because Person derives Eq
  person == person

def value_given_verified_enum: Unit =
  enum Weekday:
    case Monday, Tuesday, Wednesday // ...

  // Compiles because Weekday is a product type with all options supporting equality
  given Eq[Weekday] = Eq.derived

  // Compiles because given Eq type class instance for Weekday is in scope
  Weekday.Monday == Weekday.Monday


def value_derives_assumed_bottom_class_hierarchy: Unit =
  class Animal
  case class Cat() extends Animal derives Eq.assumed
  case class Dog() extends Animal

  val cat = Cat()
  val dog = Dog()

  // Compiles because the selected bottom class of this hierarchy derives Eq
  cat == cat

  // dog == dog
  // ERROR: Values of types Dog and Dog cannot be compared with == or !=

  // cat != dog
  // ERROR: Values of types Cat and Dog cannot be compared with == or !=


def value_derives_assumed_base_class: Unit =
  class Animal derives Eq.assumed
  case class Cat() extends Animal
  case class Dog() extends Animal

  val cat = Cat()
  val dog = Dog()

  // Compiles because the base class of this hierarchy derives Eq
  cat == cat
  dog == dog
  cat != dog


def value_given_assumed_arbitrary_type: Unit =
  import java.util.jar.Attributes

  given Eq[Attributes] = Eq.assumed

  val attributes = Attributes()

  // Compiles because given Eq type class instance for Attributes is in scope
  attributes == attributes


def reference_derives_verified: Unit =
  // Compiles because Item cannot be extended and does not override the equals() method
  class Item(val id: String) derives EqRef

  val item = Item("")

  // Compiles because given EqRef type class instance for Item is in scope
  item eqRef item


def reference_given_verified: Unit =
  class Item(id: String)

  // Compiles because Item is cannot be extended and overrides the equals() method
  given EqRef[Item] = EqRef.derived

  val item = Item("")

  // Compiles because given EqRef type class instance for Item is in scope
  item neRef item


def reference_derives_assumed: Unit =
  class Item(val id: String) derives EqRef.assumed

  val item = Item("")

  // Compiles because Item derives EqRef
  item eqRef item


def reference_given_assumed: Unit =
  class Item(val id: String)

  given EqRef[Item] = EqRef.assumed

  val item = Item("")

  // Compiles because given EqRef type class instance for Item is in scope
  item neRef item


def standard_instances: Unit =
  import java.time.{LocalDate, LocalDateTime}

  val now = LocalDateTime.now
  val later = LocalDateTime.now

  // Compiles out of the box
  now == later

  val today = LocalDate.now

  // today != now
  // ERROR: Values of types LocalDate and LocalDateTime cannot be compared with == or !=


def collection_extensions:Unit =
  case class Apple(x: String) derives Eq
  val appleA = Apple("A")
  val appleB = Apple("B")
  val apples = List(appleA, appleB)

  case class Car(x: String) derives Eq
  val carY = Car("Y")
  val carX = Car("X")
  val cars = List(carX, carY)

  // Compiles but it should not since it is meaningless and always returns false
  apples.contains(carX)

  // apples.contains_eq(carX)
  // ERROR: Values of types A and A cannot be compared with == or !=
  //        where: A is a type variable with constraint >: Apple | Car

  // Compiles but it should not since it is meaningless and always returns the original list
  apples.diff(cars)

  // apples.diff_eq(cars)
  // ERROR: Values of types Apple and Apple | Car cannot be compared with == or !=


def hybrid_equality: Unit =
  // Import to disable strict equality
  import equality.hybrid.given

  class Item(val id: String) derives EqRef.assumed

  val item = Item("")

  // Compiles because hybrid equality is enabled
  item == item


def universal_equality: Unit =
  // Import to disable strict equality
  import equality.universal.given

  // Compiles because universal equality is enabled
  1 == true


def number_equality: Unit =
  // Covariant type parameter T
  case class Box[+T: Eq](value: T) derives Eq

  val box1 = Box(Seq(Some( (true, 'a', 1  ) )))
  val box2 = Box(Seq(Some( (true, 'a', 1L ) )))

  // Compiles because type parameter T is covariant and an instance of
  // Eq[ Box[Seq[Option[ (Boolean, Char, AnyNumber) ]]] ]
  // is available and can be applied both to Eq[box1.type] and Eq[box2.type].
  box1 == box2
