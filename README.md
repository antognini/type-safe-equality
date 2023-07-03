**Scala 3 type safe equality**

Provides type safe equality features allowing developers to fully leverage compiler strict equality support.

This makes equality behave in a standard and intuitive way equivalent to other modern programming languages.

* [Eq type class](#eq-type-class) - strict equality type class with automatic derivation for case classes
* [Standard Eq instances](#standard-eq-instances) - equality type class instances for relevant Java and Scala standard library types
* [Collection extensions](#collection-extensions) - equality-safe extension methods for standard Scala collections
* [Strict equality opt-out](#strict-equality-opt-out) - escape hatch to enable universal equality within a specific scope
* [Reference equality](#reference-equality) - equality-safe reference comparison

<br/>

![](https://github.com/antognini/type-safe-equality/blob/eq/site/example-ide-1a.png)

<br/>

Further considerations on strict equality are discussed [here](#strict-equality-considerations). 

# Getting started

This library requires **[Scala 3.3](https://scala-lang.org/blog/2023/05/30/scala-3.3.0-released.html)+** on **[Java 11](https://en.wikipedia.org/wiki/Java_version_history)+**. 

Include the library dependency in your `build.sbt` and enable strict equality:
```scala
libraryDependencies += "ch.produs" %% "type-safe-equality" % "0.2.0"

// Not absolutely necessary but strongly recommended
scalacOptions += "-language:strictEquality"
```
Try it out!

```scala
import equality.{*, given}
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
  item: A
) derives Eq
val box = Box("my box", Attributes())
box == box

// Use an equality-safe alternative to .contains()
val names = List(now, now)
names.contains_safe(now)
```

# Features
The examples shown below assume strict equality enabled (unless otherwise stated).

## Eq type class
[Eq](https://github.com/antognini/type-safe-equality/blob/main/main/src/main/scala/equality/Eq.scala) 
is a type class providing type safe use of the `==` and `!=` operators.

Eq instances can be obtained in the following ways:

* By asking the compiler to **verify** (derive) equality safety
  * `derives Eq` 
  * `given Eq[X] = Eq.derived`

* By telling the compiler to **assume** equality safety (fallback with no checking)
  * `derives Eq.assumed`
  * `given Eq[X] = Eq.assumed`

**Note**: It is recommended to use the assumed equality only if the verification is not possible.


### Verifying equality

Verified equality for composed case classes via type class derivation:
```scala
import equality.{*, given}

case class Email( address:String) derives Eq

// Only compiles because class Email derives Eq
case class Person(
  name: String,
  contact: Email,
) derives Eq

val person = Person("Alice", Email("alice@behappy.com"))

// Only compiles because class Person derives Eq
person == person
```

Verified equality for composed case classes with type parameters via type class derivation:
```scala
import equality.{*, given}

case class Email( address:String) derives Eq

// Only compiles because the type parameter A is declared with a context bound [A: Eq]
case class Person[A: Eq](
  name: String,
  contact: A,
) derives Eq

// Only compiles because class Email derives Eq
val person = Person("Alice", Email("alice@behappy.com"))

person == person
```

Verified equality for an existing arbitrary class with a given using the same derivation mechanism:
```scala
import equality.{*, given}

// Only compiles because SomeProduct conforms to the equality rules
given Eq[SomeProduct] = Eq.derived

// Only compiles with the given instance above
SomeProduct() == SomeProduct()
```

### Assuming equality

Assumed equality for the bottom classes of a class hierarchy via type class derivation:
```scala
import equality.{*, given}

abstract class Animal
case class Cat() extends Animal derives Eq.assumed
case class Dog() extends Animal derives Eq.assumed

// Values of type Cat can compare each other with == and != 
// Values of type Dog can compare each other with == and != 
// Within this hierarchy, any other comparison with == and != fails
```

Assumed equality for the base class of a class hierarchy via type class derivation:
```scala
import equality.{*, given}

abstract class Animal derives Eq.assumed
case class Cat() extends Animal
case class Dog() extends Animal

// Within this hierarchy, any value can compare to any other value with == and !=
```

Assumed equality for an existing arbitrary class with a given: 
```scala
import equality.{*, given}
import java.util.jar.Attributes

// Always compiles
given Eq[Attributes] = Eq.assumed

// Only compiles with the given instance above
Attributes() == Attributes()
```

### Eq verification rules

In order to successfully verify (derive) equality for a type `T` all of following conditions must be satisfied:

1. Type `T` is a `Product`
2. For each field of type `F`:
    1. A given instance of `Eq[F]` is available in the current scope
    2. For each type parameter `P` used within `F`:
       1. `P` is declared with a context bound `[P: Eq]`

Equality verification rules examples:
```scala
// Rule 1: a case class is a Product
case class MyClass[A: Eq, B: Eq, C: Eq, D: Eq](
                       
  // Rule 2.i       given instance of Eq[OtherClass] is available in the current scope  
  other: OtherClass,

  // Rule 2.i       given instance of Eq[?] is available in the current scope  
  // Rule 2.ii.a    A is declared with a context bound [A: Eq]                               
  a: A,

  // Rule 2.i       given instance of Eq[Box[?]] is available in the current scope
  // Rule 2.ii.a    B is declared with a context bound [B: Eq]                               
  box: Box[B],
                                       
  // Rule 2.i       given instance of Eq[Couple[?, Seq[?]]] is available in the current scope
  // Rule 2.ii.a    C is declared with a context bound [C: Eq] 
  // Rule 2.ii.a    D is declared with a context bound [D: Eq]                               
  couple: Couple[C, Seq[D]]
                                       
) derives Eq
```


## Standard Eq instances

This library provides **[Eq type class instances](StandardEqInstances.md)** for selected Java and Scala standard library types.

Standard type class `Eq` instances work out of the box and provide type safe equality at no cost

**Note**: `Eq` instances are provided only for those standard library types where equality operation is guaranteed to make sense.

```scala
import equality.{*, given}
import java.time.{LocalDate, LocalDateTime}

val now = LocalDateTime.now
val later = LocalDateTime.now

// Compiles out of the box
now == later

val today = LocalDate.now

today == now
// ERROR: Values of types LocalDate and LocalDateTime cannot be compared with == or !=
```

### Selective Eq instance import

```scala
// All equality type class instances
import equality.given

// All Scala equality type class instances
import equality.scala_all.given

// All Java equality type class instances
import equality.java_all.given

// All equality type class instances for package java.time 
import equality.java_time.given

// Equality type class instance for java.time.LocalDate
import equality.java_time_LocalDate
```

### Creating your group of equality type class instances
```scala
package mypackage
import equality.{*, given}

// All Eq defined for package java.util + other 3 predefined + 1 on your own
object MyEqInstances:

   // Package level wildcard exports (.* and .given) are not allowed in Scala, EqInstances.given must be used
   export equality.java_util.EqInstances.given
   
   export equality.java_io_File
   export equality.java_nio_file_Path
   export equality.scala_Array
   
   given Eq[java.util.jar.Attributes] = Eq.assumed
```

subsequently, use `MyEqInstances` anywhere with
```scala
import mypackage.MyEqInstances.given
```


## Collection extensions

Certain methods of specific standard library collection types (and their subtypes) are not equality-safe.

This library provides the following equality-safe alternatives for such methods:

| Collection types                                                                                                                                                                                                                | Original method     | Equality-safe method     |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|--------------------------|
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html) , [Iterator](https://scala-lang.org/api/3.x/scala/collection/Iterator.html)                                                                                     | `.contains`         | `.contains_safe`         |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.containsSlice`    | `.containsSlice_safe`    |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.diff`             | `.diff_safe`             |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html) , [Iterator](https://scala-lang.org/api/3.x/scala/collection/Iterator.html)                                                                                     | `.indexOf`          | `.indexOf_safe`          |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.indexOfSlice`     | `.indexOfSlice_safe`     |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.intersect`        | `.intersect_safe`        |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.lastIndexOf`      | `.lastIndexOf_safe`      |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.lastIndexOfSlice` | `.lastIndexOfSlice_safe` |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html) , [Iterator](https://scala-lang.org/api/3.x/scala/collection/Iterator.html),  [IterableOnce](https://scala-lang.org/api/3.x/scala/collection/IterableOnce.html) | `.sameElements`     | `.sameElements_safe`     |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.search`           | `.search_safe`           |


**Note**: [Set](https://scala-lang.org/api/3.x/scala/collection/Set.html) and [Map](https://scala-lang.org/api/3.x/scala/collection/Map.html) are generally equality-safe because they use invariant type parameters.

:warning: This feature requires enabling strict equality compiler option

An example of using equality-safe collection methods:
```scala
import equality.{*, given}

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

apples.contains_safe(carX) 
// ERROR: Values of types A and A cannot be compared with == or !=
//        where: A is a type variable with constraint >: Apple | Car

// It is pointless to remove a list of cars from a list of apples
apples.diff(cars)      
// Type checks but it shouldn't --> returns the original list

apples.diff_safe(cars)
// ERROR: Values of types Apple and Apple | Car cannot be compared with == or !=
```

## Strict equality opt-out

In order to facilitate the transition to strict equality on existing codebases this library provides an opt-out from strict equality checking.

Strict equality opt-out for an entire compilation unit:
```scala
// Import to disable strict equality
import equality.universal.given

// Only compiles because strict equality is disabled
val _ = 1 == "abc"
```

Strict equality opt-out for a local scope:
```scala
def areEqual(x1: Any, x2: Any): Boolean =
  // Import to locally disable strict equality
  import equality.universal.given

  // Only compiles because strict equality is disabled
  x1 == x2
```

## Reference equality

The built-in `eq` and `ne` operators for comparing references are not equality-safe. 
Therefore this library also provides equality-safe comparison of references via the [EqRef](https://github.com/antognini/type-safe-equality/blob/main/main/src/main/scala/equality/EqRef.scala) helper.

```scala
val myThing = MyThing()
//     ^         ^   
// reference   value
```

Equality-safe reference comparison:
```scala
import equality.{*, given}
import java.time.{LocalDate, LocalDateTime}

val today = LocalDate.now
val now = LocalDateTime.now

// Compiles because references are not equality-safe
today eq now

val eqRef = EqRef[LocalDate]
import eqRef.*

// Compiles because references are of the same type
today equalRef today
today notEqualRef today

today equalRef now
// ERROR: Found (now : LocalDateTime); Required: LocalDate
```

# Motivation

Excluding some elementary types, strict equality disallows values of the same type from comparing each other with `==` and `!=` *out of the box*.
At first sight this may be seen as an unneeded complication with no benefits, but it provides an additional level of type safety.

For example, it can be problematic to compare functions with `==`:
```scala
type F = Int => Int
val f1:F = (x:Int) => x + 1
val f2:F = (x:Int) => x + 1

// Compiles with strict equality disabled
// Returns the same as f1 eq f2 (comparison for reference equality, not value equality)
f1 == f2
```

What is the intention behind `f1 == f2`: to compare reference equality (which should evaluate to `false`), or to compare functional equality (which should evaluate to `true`)? 
There is no way to compare functional equality in Scala, so it could possibly be a programming error. With strict equality checking enabled, the compiler can help assist in identifying such cases:
```scala
// Strict equality enabled
f1 == f2 
// ERROR: Values of types F and F cannot be compared with == or !=
```
If the intention is to compare the functions for [reference equality](#reference-equality), it can be done with `f1 equalRef f2` or by *locally* allowing `f1 == f2` (after realizing `==` boils down to reference equality).
Similar pitfalls may happen for traits `Future`, `Promise`, `ServerSocket` and other structures which are critical to equal with `==` and `!=`.

However, it can become tedious to declare strict equality type class instances for types like `Array`, `LocalDate`, `File`, or `Duration` (either Scala or Java `Duration`), which is one of the motivations for this library.

# Strict equality considerations

## Number types

Scala allows to freely compare values of types `Byte`, `Char`, `Short`, `Int`, `Long`, `BigInt`, `Float`, `Double`, `BigDecimal` with one another, also in strict equality enabled:

```scala
1 == 1L
```

This is how composed numbers behave with strict equality enabled without `Eq`:
```scala
// Covariant type parameter
case class Box[+A](a: A)

Box(1) == Box(1L)
// ERROR: Values of types Box[Int] and Box[Long] cannot be compared with == or !=
```

This is how composed numbers behave with strict equality enabled with `Eq`:
```scala
import equality.{*, given}

// Covariant type parameter
case class Box[+A: Eq](a: A) derives Eq

// Compiles because Eq type class is derived 
Box(1) == Box(1L)
```

This works because the library defines an `Eq` instance for the union of all number types:
```scala
package equality.scala_

type AnyNumber = Byte | Char | Short | Int | Long | BigInt | Float | Double | BigDecimal
given scala_AnyNumber: Eq[AnyNumber] = Eq.assumed
```

Another example:
```scala
import equality.{*, given}

// Covariant type parameter A
case class Box[+A: Eq](a: A) derives Eq

val box1 = Box(Seq(Some( (true, 'a', 1  ) )))
val box2 = Box(Seq(Some( (true, 'a', 1L ) )))

// Compiles because type parameter A is covariant and an instance of
// Eq[ Box[Seq[Option[ (Boolean, Char, AnyNumber) ]]] ]
// is available and can be applied both to Eq[box1.type] and Eq[box2.type].
box1 == box2
```

## Type Any

Using `Any` as part of any type declaration with strict equality enabled will cause that type not to be comparable with `==` and `!=`.  

## Enums

Enums are products and therefore equality can be obtained via `Eq` type class derivation.


```scala
import equality.{*, given}

enum Weekday derives Eq:
  // These are instances of the product type Weekday
  case Monday, Tuesday, Wednesday, // ...

// Only compiles because enum Weekday derives Eq
val myDay: Weekday = Weekday.Monday
myDay == myDay

```

## Checking strict equality build settings

To guarantee the build has `-language:strictEquality` enabled, include this in your sources:

```scala
import equality.{*, given}

// Does not need to be called, it fails to compile with strict equality turned off
checkStrictEqualityBuild()
```

# Limitations

## Missing constructor type decomposition 

In the example below, the `Eq` type class derivation does not decompose the constructor parameter type `Seq[B]` to check if it contains type parameters (it actually contains type parameter `B`).

```scala
// [B: Eq] should be enforced, too
case class Box[A: Eq, B](
  a: A,
  b: Seq[B]
) derives Eq

case class Cup() derives Eq
case class Spoon() // no derives

// Error should be detected here, but it is not because of the missing [B: Eq]
val box = Box( Cup(), Seq( Spoon()))

// The operator correctly fails, but the error is reported too late in the compilation process 
box == box
// ERROR: Values of types Box[Cup, Spoon] and Box[Cup, Spoon] cannot be compared with == or !=.
```

Special thanks to
* **Martin Ockajak**