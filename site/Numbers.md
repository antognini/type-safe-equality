**[Type safe equality for Scala 3](https://github.com/antognini/type-safe-equality)**

## Union type for numbers

A list of `Int` and `BigDecimal` values can be typed in different ways:
```scala
import java.lang.Number

val listA: List[Any]               = List(1, BigDecimal(2))
val listB: List[Number]            = List(1, BigDecimal(2))
val listC: List[Int | BigDecimal]  = List(1, BigDecimal(2))
```  
But what does the compiler infer, if we do not specify the type explicitly?
```scala
// what type does list have?
val list = List(1, BigDecimal(2))
```  

The compiler infers `List[Int | BigDecimal]`.

This library defines following union types, which are used for given `Eq` instances:
```scala
package equality.scala_

type AnyNumber = Byte | Char | Short | Int | Long | BigInt | Float | Double | BigDecimal

type AnyJavaNumber = java.lang.Byte     | java.lang.Character   | java.lang.Short       |
                     java.lang.Integer  | java.lang.Long        | java.math.BigInteger  |
                     java.lang.Float    | java.lang.Double      | java.math.BigDecimal
```

Now the list can be declared like follows:
```scala
import equality.{*, given}

// Compiles because `Int | BigDecimal` is more specific than AnyNumber
val list: List[AnyNumber] = List(1, BigDecimal(2))
```
  
## Givens for number union types

To facilitate the comparison between numbers of different types, this library defines the following givens:
```scala
package equality.scala_

given scala_AnyNumber: Eq[AnyNumber] = Eq.assumed
given scala_AnyJavaNumber: Eq[AnyJavaNumber] = Eq.assumed
given scala_AnyScalaOrJavaNumber: Eq[AnyNumber | AnyJavaNumber] = Eq.assumed
```  

## Using type parameters as numbers with `CanEqual`

Scala allows to freely compare values of types `Byte`, `Char`, `Short`, `Int`, `Long`, `BigInt`, `Float`, `Double`, `BigDecimal` for equality with one another, also with strict equality enabled:

```scala
1 == BigDecimal(2L)
```

## CanEqual derivation with a numeric type supplied as an invariant type parameter

Invariant type parameters number composition with `CanEqual`:
```scala
// Invariant type parameter A
case class Box[A](a: A) derives CanEqual

// Compiles out of the box
Box(1) == Box(BigDecimal(2))
```

## Eq derivation with a numeric type supplied as an invariant type parameter

Invariant type parameter number composition failure with `Eq`:
```scala
import equality.{*, given}

// Invariant type parameter A
case class Box[A: Eq](a: A) derives Eq

Box(1) == Box(BigDecimal(2))
// ERROR: Values of types Box[Int] and Box[BigDecimal] cannot be compared with == or !=.
```

This is more a feature than a bug since it allows you to spot type inconsistencies.

Invariant type parameter number composition with `Eq`:
```scala
import equality.{*, given}

// Invariant type parameter A
case class Box[A: Eq](a: A) derives Eq, CanEqual

// Compiles because class Box additionally derives CanEqual 
Box(1) == Box(BigDecimal(2))
```

## Eq derivation with a numeric type supplied as an covariant type parameter

Covariant type parameter number composition with `Eq`:
```scala
import equality.{*, given}

// Covariant type parameter A
case class Box[+A: Eq](a: A) derives Eq

// Compiles because type parameter A is covariant and 
// an instance of Eq[Box[AnyNumber]] is available and 
// it can be applied both to Eq[Box[Int]] and Eq[Box[BigDecimal]].
Box(1) == Box(BigDecimal(2))
```

Another example with a covariant type parameter:
```scala
import equality.{*, given}

// Covariant type parameter A
case class Box[+A: Eq](a: A) derives Eq

val box1 = Box(Seq(Some  ( (true,   'a',   1             ) )))
//         ^   ^   ^        ^        ^     ^
val box2 = Box(Seq(Some  ( (true,   'a',   BigDecimal(2) ) )))
//         ^   ^   ^        ^        ^     ^
//     Eq[ Box[Seq[Option[ (Boolean, Char, AnyNumber     ) ]]] ] 

// Compiles because the shown Eq instance is available and
// can be applied both to Eq[box1.type] and Eq[box2.type].
box1 == box2
```
