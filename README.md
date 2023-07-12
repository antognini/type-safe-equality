**Type safe equality for Scala 3**

This library aims to prevent compilation of code comparing arbitrary values unless equality is specifically supported for their respective types.

* Any operations adhering to the principle above are considered to be **equality-safe**.
* A `Product` type (case class, enum, tuple) can support equality only if the types of all its fields support equality.

This makes equality behave like using [Data.Eq](https://hackage.haskell.org/package/base-4.18.0.0/docs/Data-Eq.html) in Haskell or [std::cmp::Eq](https://doc.rust-lang.org/beta/std/cmp/trait.Eq.html) in Rust.

The following features are provided based on the compiler [strict equality](https://docs.scala-lang.org/scala3/book/ca-multiversal-equality.html#allowing-the-comparison-of-class-instances) support:
* [Eq type class](#eq-type-class) - strict equality type class with automatic derivation for `Product` types
* [Standard Eq instances](#standard-eq-instances) - equality type class instances for relevant Java and Scala standard library types
* [Collection extensions](#collection-extensions) - equality-safe extension methods for standard Scala collections
* [Strict equality opt-out](#strict-equality-opt-out) - escape hatch to enable universal equality within a specific scope
* [Reference equality](#reference-equality) - equality-safe reference comparison

<br/>

![](https://github.com/antognini/type-safe-equality/blob/main/site/example-ide-1h.png)

<br/>

Please see the [FAQ](#faq) and [further considerations](#strict-equality-considerations) on strict equality for additional information.


# Quickstart

## New Project

```scala
sbt new antognini/type-safe-equality.g8
cd type-safe-equality-example
sbt run
```

## REPL

Execute any of the examples by copy & paste them in REPL:
```scala
sbt new antognini/type-safe-equality.g8
cd type-safe-equality-example
sbt console
```

## Embed in your project

This library requires **[Scala 3.3](https://scala-lang.org/blog/2023/05/30/scala-3.3.0-released.html)+** on **[Java 11](https://en.wikipedia.org/wiki/Java_version_history)+**. 

Include the library dependency in your `build.sbt` and enable strict equality:
```scala
libraryDependencies += "ch.produs" %% "type-safe-equality" % "0.4.1"

scalacOptions += "-language:strictEquality"
scalacOptions += "-Yimports:scala,scala.Predef,java.lang,equality.all"
```

## Try it out!

```scala
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
  item: Either[String, A],
) derives Eq
val box = Box("my box", Right(Attributes()))
box == box

// Use an equality-safe alternative to .contains()
val names = List(now, now)
names.contains_eq(now)
```

# Features

The examples shown below assume strict equality enabled (unless otherwise stated).

## Eq type class

[Eq](https://github.com/antognini/type-safe-equality/blob/main/main/src/main/scala/equality/Eq.scala) 
is a type class providing type safe use of the `==` and `!=` operators and verification of equality safety for composed data types.

Eq instances can be obtained in the following ways:

* By asking the compiler to **verify** (derive) equality safety
  * `derives Eq` 
  * `given Eq[X] = Eq.derived`
    
* By telling the compiler to **assume** equality safety (fallback with no checking)
  * `derives Eq.assumed`
  * `given Eq[X] = Eq.assumed`

**Note**: It is recommended to assume equality only if it is not possible to verify it.


### Verifying equality

Verified equality for composed case classes via type class derivation:
```scala
case class Email( address:String) derives Eq

// Only compiles because class Email derives Eq
case class Person(
  name: String,
  contact: Email,
) derives Eq

val person = Person("Alice", Email("alice@maluma.osw"))

// Only compiles because class Person derives Eq
person == person
```

Verified equality for composed case classes with type parameters via type class derivation:
```scala
case class Email( address:String) derives Eq

// Only compiles because the type parameter A is declared with a context bound [A: Eq]
case class Person[A: Eq](
  name: String,
  contact: A,
) derives Eq

// Only compiles because class Email derives Eq
val person = Person("Alice", Email("alice@maluma.osw"))

person == person
```

Verified equality for an existing arbitrary class with a given using the same derivation mechanism:
```scala
// Only compiles because SomeProduct conforms to the equality rules
given Eq[SomeProduct] = Eq.derived

// Only compiles with the given instance above
SomeProduct() == SomeProduct()
```


### Assuming equality

Assumed equality for the bottom classes of a class hierarchy via type class derivation:
```scala
class Animal
case class Cat() extends Animal derives Eq.assumed
case class Dog() extends Animal derives Eq.assumed

// Values of type Cat can compare each other with == or != 
// Values of type Dog can compare each other with == or != 
// Within this hierarchy, any other comparison with == or != fails
```

Assumed equality for the base class of a class hierarchy via type class derivation:
```scala
class Animal derives Eq.assumed
case class Cat() extends Animal
case class Dog() extends Animal

// Within this hierarchy, any value can compare to any other value with == or !=
```

Assumed equality for an existing arbitrary class with a given: 
```scala
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

This library provides **[Eq type class instances](site/StandardEqInstances.md)** for selected Java and Scala standard library types.

Standard type class `Eq` instances work out of the box and provide type safe equality at no cost

**Note**: `Eq` instances are provided only for those standard library types where equality operation is guaranteed to make sense.

```scala
import java.time.{LocalDate, LocalDateTime}

val now = LocalDateTime.now
val later = LocalDateTime.now

// Compiles out of the box
now == later

val today = LocalDate.now

today == now
// ERROR: Values of types LocalDate and LocalDateTime cannot be compared with == or !=
```

## Collection extensions

Certain methods of specific standard library collection types (and their subtypes) are not equality-safe.

This library provides the following equality-safe alternatives for such methods:

| Collection types                                                                                                                                                                                                                | Original method     | Equality-safe method     |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|--------------------------|
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html) , [Iterator](https://scala-lang.org/api/3.x/scala/collection/Iterator.html)                                                                                     | `.contains`         | `.contains_eq`         |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.containsSlice`    | `.containsSlice_eq`    |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.diff`             | `.diff_eq`             |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html) , [Iterator](https://scala-lang.org/api/3.x/scala/collection/Iterator.html)                                                                                     | `.indexOf`          | `.indexOf_eq`          |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.indexOfSlice`     | `.indexOfSlice_eq`     |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.intersect`        | `.intersect_eq`        |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.lastIndexOf`      | `.lastIndexOf_eq`      |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.lastIndexOfSlice` | `.lastIndexOfSlice_eq` |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html) , [Iterator](https://scala-lang.org/api/3.x/scala/collection/Iterator.html),  [IterableOnce](https://scala-lang.org/api/3.x/scala/collection/IterableOnce.html) | `.sameElements`     | `.sameElements_eq`     |
| [Seq](https://scala-lang.org/api/3.x/scala/collection/Seq.html)                                                                                                                                                                 | `.search`           | `.search_eq`           |


**Note**: [Set](https://scala-lang.org/api/3.x/scala/collection/Set.html) and [Map](https://scala-lang.org/api/3.x/scala/collection/Map.html) are generally equality-safe because they use invariant type parameters.

:warning: This feature requires enabling strict equality compiler option

An example of using equality-safe collection methods:
```scala
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

apples.contains_eq(carX) 
// ERROR: Values of types A and A cannot be compared with == or !=
//        where: A is a type variable with constraint >: Apple | Car

// It is pointless to remove a list of cars from a list of apples
apples.diff(cars)      
// Type checks but it shouldn't --> returns the original list

apples.diff_eq(cars)
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


# Library feature selection

This library relies on compiler imports to facilitate working on equality without imports in your code. However, you can choose which features you want to make available in your code. 

## All-in-one

```scala
scalacOptions += "-Yimports:scala,scala.Predef,java.lang,equality.all"
```

## Minimal

With this setup you will only see the two basic classes of this library in your code. Everything else needs to be explicitly imported:

```scala
scalacOptions += "-Yimports:scala,scala.Predef,java.lang,equality"
```
Now you need to import every feature you want to use:

```scala
// All Scala equality type class instances
import equality.scala_all.given

// All Java equality type class instances
import equality.java_all.given

// All equality type class instances for package java.time 
import equality.java_time.given

// Equality type class instance for java.time.LocalDate
import equality.java_time.java_time_LocalDate

// Equality-safe collection extension
import equality.scala_collection.CollectionExtension.*
```
## Customized

```scala
scalacOptions += "-Yimports:scala,scala.Predef,java.lang,equality"
```
Now you can define an import object:
```scala
object MyEq {
  // all Eq instances for scala.*
  export equality.scala_all.EqInstances.given

  // all Eq instances for java.util.*
  export equality.java_util.EqInstances.given

  // Eq instance for java.io.File
  export equality.java_io.java_io_File

  // Eq instance for java.nio.file.Path
  export equality.java_nio_file.java_nio_file_Path

  // additionally defined Eq instance 
  given Eq[java.util.jar.Attributes] = Eq.assumed
}
```

subsequently, use `MyEq` anywhere with
```scala
import MyEq.{*, given}
```


# Strict equality considerations

## Type Any

Using `Any` as part of any type declaration with strict equality enabled will cause that type not to be comparable with `==` or `!=`.  

## Enums

Enums are products and therefore equality can be obtained via `Eq` type class derivation.


```scala
enum Weekday derives Eq:
  // These are instances of the product type Weekday
  case Monday, Tuesday, Wednesday // ...

// Only compiles because enum Weekday derives Eq
val myDay: Weekday = Weekday.Monday
myDay == myDay
```

## Checking strict equality build settings

To guarantee the build has `-language:strictEquality` enabled, include this in your sources:

```scala
// Does not need to be called, it fails to compile with strict equality turned off
checkStrictEqualityBuild()
```


# FAQ

## What is the relation between `Eq` and `CanEqual`?

Each instance of `Eq` type class produces an instance of `CanEqual` thus making it compatible with established strict equality support in the Scala compiler.


## Is `CanEqual` good enough to model equality?

`CanEqual` is a fairly low-level marker mechanism with following limitations:

* `CanEqual` does not support compile-time verification of equality safety for product types composed from other equality-safe types.
* `CanEqual` given instances are automatically provided for a few basic standard library types but for nothing else.

Composition example with `CanEqual` not failing as it should:
```scala
case class A()

// Compiles but should not compile since member type A neither derives CanEqual nor is there a given CanEqual instance for it
case class B(a: A) derives CanEqual

B(A()) == B(A())
```

## Why does `CanEqual[-L, -R]` have two type parameters and `Eq[-T]` only one ?

See the motivation for `CanEqual[-L, -R]` in the documentation about [Scala 3 equality](https://docs.scala-lang.org/scala3/reference/contextual/multiversal-equality.html).

This library focuses on strict equality and therefore the `Eq` type class can be expressed with a single type parameter.


## Why is the type parameter in `Eq` contravariant ?

`Eq[-T]` reflects the principle by which `given Eq[A]` allows any equality comparison between values of type `A` or any type more specialized than `A`.

* For type `B` which extends type `A`, `given Eq[A]` allows pairwise comparison between values of `A` or `B`.
* For unrelated types `A` and `B`, `given Eq[A | B]` allows pairwise comparison between values of `A`, `B` or `A | B`.
* For unrelated types `A` and `B`, `given Eq[A]` allows pairwise comparison between values of `A`, or `A & B`.


## Why do `enum` types need to derive `Eq` to be equality-safe ?

It does not seem to be possible to create given Eq instances for enum types automatically in Scala 3 without using a compiler plugin.


## Why does comparing two instances of the same class using different numeric type parameters not compile ?

This library follows a principle that values of different types are not comparable. 
This includes numeric types so this is a feature and not a bug.

Contrary to this principle, the compiler makes values of numeric types universally comparable even with strict equality enabled.

If universal equality for case classes parameterized with different numeric types is required, the following method can be used: 
```scala
case class Box[A: Eq](a: A) derives Eq, CanEqual

// Compiles because class Box additionally derives CanEqual 
Box(1) == Box(2L)
```

##


Special thanks to
* **Martin Ockajak**