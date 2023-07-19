**Type safe equality for Scala 3**

This library aims to prevent compilation of code comparing arbitrary values unless equality is specifically supported for their respective types.

* Any operations adhering to the principle above are considered to be *equality-safe*.
* A `Product` type (case class, enum, tuple) can support equality only if the types of all its fields support equality.

This makes equality behave similarly to [Data.Eq](https://hackage.haskell.org/package/base-4.18.0.0/docs/Data-Eq.html) in Haskell or [std::cmp::Eq](https://doc.rust-lang.org/beta/std/cmp/trait.Eq.html) in Rust.

The following features are provided leveraging the compiler [strict equality](https://docs.scala-lang.org/scala3/book/ca-multiversal-equality.html#allowing-the-comparison-of-class-instances) support:
* [Eq type class](#eq-type-class) - **value equality** type class with automatic **derivation** for `Product` types
* [EqRef type class](#eqref-type-class) - **reference equality** type class with automatic **derivation** for non-extensible types
* [Standard equality instances](#standard-instances) - equality **support** for relevant Java and Scala **standard library types**
* [Collection extensions](#collection-extensions) - **equality-safe** extension methods for standard Scala **collections**
* [Hybrid equality](#hybrid-equality) - **combined** use of equality constructs for both **value and reference equality**
* [Universal equality](#universal-equality) - **escape hatch** to enable **default Scala behavior** concerning equality

<br/>

![](https://github.com/antognini/type-safe-equality/blob/main/doc/example-ide-1k.png)

<br/>

Please see [feature selection](#feature-selection) and [FAQ](#faq) for additional information.


# Quickstart

## New Project

Execute the following commands:
```scala
sbt new antognini/type-safe-equality.g8
cd type-safe-equality-example
sbt console
```

Run any of the examples shown below by copy & pasting them into REPL.


## Embed in your project

This library requires **[Scala 3.3](https://scala-lang.org/blog/2023/05/30/scala-3.3.0-released.html)+** on
**[Java 11](https://en.wikipedia.org/wiki/Java_version_history)+**. 

Include the library dependency in your `build.sbt` and enable strict equality:
```scala
libraryDependencies += "ch.produs" %% "type-safe-equality" % "0.6.0"

scalacOptions += "-language:strictEquality"
scalacOptions += "-Yimports:scala,scala.Predef,java.lang,equality"
```

Try it out:
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
case class Box[T: Eq](
  name: String,
  item: Either[String, T],
) derives Eq
val box = Box("", Right(Attributes()))
box == box

// Use an equality-safe alternative to .contains()
val names = List(now, now)
names.contains_eq(now)
```


# Features

All features described below assume that strict equality compiler flag is enabled.


## Eq type class

[Eq](https://github.com/antognini/type-safe-equality/blob/main/equality/src/main/scala/equality/core/Eq.scala) 
is a type class providing type safe use of `==` and `!=` as *value equality* operators and
automatic verification of *value equality* support for product types.

Eq instances for an arbitrary type `T` can be obtained in one of the following ways:

* By asking the compiler to **verify** (derive) *value equality* support
  * `<type T> derives Eq` 
  * `given Eq[T] = Eq.derived`
    
* By telling the compiler to **assume** *value equality* support (fallback with no checking)
  * `<type T> derives Eq.assumed`
  * `given Eq[T] = Eq.assumed`

**Note**: It is recommended to assume *value equality* support only if it is not possible to verify it.

Eq limits the `==` and `!=` operators to compare values only and
only if given Eq instance is in scope for the compared type.

This causes the following differences from the default Scala behavior:
- It completely disallows comparison of values for unrelated or unsupported types.
- It practically disables the use `==` and `!=` operators for comparing references.


### Verifying equality

Verify equality for composed case classes via type class derivation:
```scala
case class Email(address: String) derives Eq

// Compiles because Email derives Eq
case class Person(
  name: String,
  contact: Email,
) derives Eq

val person = Person("Alice", Email("alice@example.net"))

// Compiles because Person derives Eq
person == person
```

Verify equality for composed case classes with type parameters via type class derivation:
```scala
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
```

Verify equality for an enum with a given using the same derivation mechanism:
```scala
enum Weekday:
  case Monday, Tuesday, Wednesday // ...

// Compiles because Weekday is a product type with all options supporting equality
given Eq[Weekday] = Eq.derived

// Compiles because given Eq type class instance for Weekday is in scope
Weekday.Monday == Weekday.Monday
```


### Assuming equality

Assume equality for the bottom classes of a class hierarchy via type class derivation:
```scala
class Animal
case class Cat() extends Animal derives Eq.assumed
case class Dog() extends Animal

val cat = Cat()
val dog = Dog()

// Compiles because the selected bottom class of this hierarchy derives Eq
cat == cat

dog == dog
// ERROR: Values of types Dog and Dog cannot be compared with == or !=

cat != dog
// ERROR: Values of types Cat and Dog cannot be compared with == or !=
```

Assume equality for the base class of a class hierarchy via type class derivation:
```scala
class Animal derives Eq.assumed
case class Cat() extends Animal
case class Dog() extends Animal

val cat = Cat()
val dog = Dog()

// Compiles because the base class of this hierarchy derives Eq
cat == cat
dog == dog
cat != dog
```

Assume equality for an existing arbitrary type with a given: 
```scala
import java.util.jar.Attributes

given Eq[Attributes] = Eq.assumed

val attributes = Attributes()

// Compiles because given Eq type class instance for Attributes is in scope
attributes == attributes
```


### Eq verification rules

In order to successfully verify (derive) equality for a type `T` all following conditions must be satisfied:

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
                                       
  // Rule 2.i       given instance of Eq[Pair[?, Seq[?]]] is available in the current scope
  // Rule 2.ii.a    C is declared with a context bound [C: Eq] 
  // Rule 2.ii.a    D is declared with a context bound [D: Eq]                               
  pair: Pair[C, Seq[D]]
                                       
) derives Eq
```


## EqRef type class

[EqRef](https://github.com/antognini/type-safe-equality/blob/main/equality/src/main/scala/equality/core/EqRef.scala)
is a type class providing type safe use of `eqRef` and `neRef` as *reference equality* operators and
automatic verification of *reference equality* support for non-extensible types.
These provide substitutes for the built-in `eq` and `ne` operators which are not type safe.

EqRef instances for an arbitrary type `T` can be obtained in one of the following ways:

* By asking the compiler to **verify** (derive) *reference equality* support **[ currently implemented but not verifying ]**
    * `<type T> derives EqRef`
    * `given EqRef[T] = EqRef.derived`

* By telling the compiler to **assume** *reference equality* support (fallback with no checking)
    * `<type T> derives EqRef.assumed`
    * `given EqRef[T] = EqRef.assumed`

**Note**: It is recommended to assume *reference equality* support only if it is not possible to verify it.

EqRef introduces `eqRef` and `neRef` methods which compare references only and
only if given EqRef instance is in scope for the compared type.

This causes the following differences from the default Scala behavior:
- It completely disallows comparison of references for unrelated or unsupported types.
  - It requires the use of `eqRef` and `neRef` operators in order to compare references.


### Verifying reference equality

Verify equality for a non-extensible class via type class derivation:
```scala
// Compiles because Item cannot be extended and does not override the equals() method
class Item(val id: String) derives EqRef

val item = Item("")

// Compiles because given EqRef type class instance for Item is in scope
item eqRef item
```

Verify equality for a non-extensible class with a given using the same derivation mechanism:
```scala
class Item(id: String)
    
// Compiles because Item is cannot be extended and overrides the equals() method
given EqRef[Item] = EqRef.derived

val item = Item("")

// Compiles because given EqRef type class instance for Item is in scope
item neRef item
```


### Assuming reference equality

Assume equality for an arbitrary class via type class derivation:
```scala
class Item(val id: String) derives EqRef.assumed

val item = Item("")

// Compiles because Item derives EqRef
item eqRef item
```

Assume equality for an arbitrary class with a given using the same derivation mechanism:
```scala
class Item(val id: String)

given EqRef[Item] = EqRef.assumed

val item = Item("")

// Compiles because given EqRef type class instance for Item is in scope
item neRef item
```


## Standard instances

This library provides **[Eq and EqRef type class instances](doc/StandardInstances.md)** for various commonly used Scala and Java standard library types.

These type class instances exists only for types where value or *reference equality* makes sense and provide type safe equality at no cost.

Use standard *value equality* type class instance to compare standard temporal values:
```scala
import java.time.{LocalDate, LocalDateTime}

val now = LocalDateTime.now
val later = LocalDateTime.now

// Compiles because given Eq type class instance for LocalDateTime is in scope
now == later

val today = LocalDate.now

today != now
// ERROR: Values of types LocalDate and LocalDateTime cannot be compared with == or !=
```


## Collection extensions

Certain methods of specific standard library collection types (and their subtypes) are not equality-safe.

This library provides the following equality-safe alternatives for such methods:

| Collection types                                                                                                                                                                                                                | Original method     | Equality-safe method   |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|------------------------|
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

Using equality-safe collection methods:
```scala
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

apples.contains_eq(carX) 
// ERROR: Values of types A and A cannot be compared with == or !=
//        where: A is a type variable with constraint >: Apple | Car

// Compiles but it should not since it is meaningless and always returns the original list
apples.diff(cars)      

apples.diff_eq(cars)
// ERROR: Values of types Apple and Apple | Car cannot be compared with == or !=
```


## Hybrid equality

Hybrid equality allows the use of value equality constructs also for reference equality.

Enabling the hybrid equality has the following effects:
* `==` and `!=` operators can also compare references if given EqRef instance is in scope for the compared type.
* Eq derivation mechanism for product types supports fields with EqRef instances.

**Note:** Mixing value and reference equality is generally discouraged and
should be limited to special cases which would cause difficulties otherwise.

Hybrid equality for a specific scope can be enabled as follows:
```scala
import equality.hybrid.given
```

Using hybrid equality:
```scala
class Item(val id: String) derives EqRef.assumed

val item = Item("")

// Compiles because hybrid equality is enabled
item == item
```


## Universal equality

Universal equality allows comparison of unrelated types which is the default Scala behavior.

Universal equality for a specific scope can be enabled as follows:
```scala
import equality.universal.given
```

Using universal equality:
```scala
// Compiles because universal equality is enabled
1 == true
```


## Feature selection

Multiple ways how to gradually enable various features of this library are described below.


Build:
```scala
scalacOptions += "-Yimports:scala,scala.Predef,java.lang,equality"
```

Build with hybrid equality:
```scala
scalacOptions += "-Yimports:scala,scala.Predef,java.lang,equality,equality.hybrid"
```

Import:
```scala
import equality.{*, given}
```

Import with hybrid equality:
```scala
import equality.{*, given}
import equality.hybrid.given
```

Import without standard Eq and EqRef instances:
```scala
import equality.core.{*, given}
```

Import with specific standard Eq and EqRef instances and collection extensions only:
```scala
import equality.core.{*, given}

// Eq type class instances for types in package scala 
import equality.scala_.{*, given}

// Eq type class instances for types in package java.time
import equality.java_time.{*, given}

// Eq type class instance for java.text.Format
import equality.java_text.java_text_Format

// EqRef type class instance for java.net.Socket
import equality.java_net.java_net_Socket

// Equality-safe collection extension
import equality.scala_collection.CollectionExtension.*
```


# FAQ

## What is the relation between `Eq` and `CanEqual`?

Each instance of `Eq` type class produces an instance of `CanEqual` thus making it compatible with the established strict equality support in the Scala compiler.


## Is `CanEqual` good enough to model equality?

`CanEqual` is a fairly low-level marker mechanism with following limitations:

* `CanEqual` does not support compile-time verification of equality safety for product types composed of other equality-safe types.
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


## Can types parametrized with `Any` support equality ?

No, they don't because they would require for the type `Any` to support equality, which would defeat the purpose.



## Why do `enum` types need to derive `Eq` to be equality-safe ?

It does not seem to be possible to create given Eq instances for enum types automatically in Scala 3 without using a compiler plugin.


## Why does comparing two instances of the same class using different numeric type parameters not compile ?

This library follows a principle that values of different types are not comparable. 
This includes numeric types so this is a feature and not a bug.

Contrary to this principle, the compiler makes values of numeric types universally comparable even with strict equality enabled.

If universal equality for case classes parameterized with different numeric types is required, the following method can be used: 
```scala
case class Box[T: Eq](value: T) derives Eq, CanEqual

// Compiles because Box also derives CanEqual 
Box(1) == Box(1L)
```


## How to verify that strict equality is enabled in build settings ?

```scala
// Call this function anywhere in your sources
checkStrictEqualityBuild()
```


##

Special thanks to
* **Martin Ockajak**
