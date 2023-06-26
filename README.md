**Scala 3 type safe equality**

This library is about facilitating the adoption of [multiversal equality](https://docs.scala-lang.org/scala3/reference/contextual/multiversal-equality.html) and transition towards strict equality.

**[  The released version 0.1.0 is a preview, focus is now on consistent equality for case classes (new Eq)  ]**

* [Eq type class](#eq-type-class) - alias for CanEqual trait with only a single type parameter 
* [Standard Eq instances](#standard-eq-instances) - equality type class instances for relevant Java and Scala standard library types
* [Collection extensions](#collection-extensions) - equality-safe extension methods for standard Scala collections

At the bottom, you can find strict equality considerations. 

The examples shown below assume strict equality is turned on (unless otherwise stated).

Special thanks to **Martin Ockajak** for painstakingly reviewing this.

# Getting Started

This library requires **[Scala 3.3](https://scala-lang.org/blog/2023/05/30/scala-3.3.0-released.html)+** on **[Java 11](https://en.wikipedia.org/wiki/Java_version_history)+**. 

Enable strict equality and include the library dependency in your `build.sbt`
```scala
libraryDependencies += "ch.produs" %% "type-safe-equality" % "0.1.0"

// not absolutely necessary but strongly recommended
scalacOptions += "-language:strictEquality"
```
Eq type class
```scala
import equality.Eq
import java.util.jar.Attributes

// making jar attributes comparable with == and != with strict equality enabled
given Eq[Attributes] = Eq

Attributes() == Attributes()
```

Standard Eq instances
```scala
import equality.given
import java.time.{LocalDate, LocalDateTime}

val now = LocalDateTime.now
val later = LocalDateTime.now
now == later

val today = LocalDate.now

today == now
// ERROR: Values of types java.time.LocalDate and java.time.LocalDateTime cannot be compared with == or !=
```

Collection extensions

```scala
import equality.collection.*

// using an equality-safe alternative to .contains()
val names = List("Alice", "Bob")
names.contains_safe("Peter")

names.contains_safe(1)
// ERROR: Values of types A and A cannot be compared with == or !=
// where: A is a type variable with constraint >: String | Int
```
# Features

## Eq type class

trait `Eq` and  value `Eq` are defined for convenience, there is no new concept behind them. Here is the definition:

```scala
package equality

type Eq[-T] = CanEqual[T, T]
val Eq = CanEqual.derived
```
This allows to simplify
```scala
given CanEqual[MyType, MyType] = CanEqual.derived

// into
given Eq[MyType] = Eq
```
and
```scala 
case class MyClass(x: Int) derives CanEqual

// into
case class MyClass(x: Int) derives Eq
```  


## Standard Eq instances
Instances for standard Java and Scala types can be selectively imported:

```scala
// all equality type class instances
import equality.given

// all equality type class instances for java.time package
import equality.java_time.given

// equality type class instance for java.time.LocalDate
import equality.java_time_LocalDate
```

### List of defined equality type class instances

| package                              | Eq instance                          | equality for type                                                                                                                    |
|--------------------------------------|--------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| `equality.java_io`                   | `java_io_File`                       | [java.io.File](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/File.html)                                       |
| `equality.java_lang`                 | `java_lang_Enum`                     | [java.lang.Enum](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Enum.html)                                   |
|                                      | `java_lang_Boolean`                  | [java.lang.Boolean](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Boolean.html)                             |
|                                      | `java_lang_Throwable`                | [java.lang.Trowable](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Throwable.html)                          |
| `equality.java_math`                 | `java_math_MathContext`              | [java.math.MathContext](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/MathContext.html)                     |
|                                      | `java_math_RoundingMode`             | [java.math.RoundingMode](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html)                   |
| `equality.java_net`                  | `java_net_URI`                       | [java.net.URI](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/URI.html)                                       |
|                                      | `java_net_URL`                       | [java.net.URL](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/URL.html)                                       |
|                                      | `java_net_HttpCookie`                | [java.net.HttpCookie](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/HttpCookie.html)                         |
|                                      | `java_net_Inet6Address`              | [java.net.Inet6Address](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/Inet6Address.html)                     |
|                                      | `java_net_Inet4Address`              | [java.net.Inet4Address](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/Inet4Address.html)                     |
|                                      | `java_net_ProtocolFamily`            | [java.net.ProtocolFamily](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/ProtocolFamily.html)                 |
|                                      | `java_net_NetworkInterface`          | [java.net.NetworkInterface](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/NetworkInterface.html)             |
|                                      | `java_net_SocketOption`              | [java.net.SocketOption](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/SocketOption.html)                     |
|                                      | `java_net_SocketOptions`             | [java.net.SocketOptions](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/SocketOptions.html)                   |
| `equality.java_nio`                  | `java_nio_ByteBuffer`                | [java.nio.ByteBuffer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/ByteBuffer.html)                         |
|                                      | `java_nio_ShortBuffer`               | [java.nio.ShortBuffer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/ShortBuffer.html)                       |
|                                      | `java_nio_IntBuffer`                 | [java.nio.IntBuffer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/IntBuffer.html)                           |
|                                      | `java_nio_LongBuffer`                | [java.nio.LongBuffer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/LongBuffer.html)                         |
|                                      | `java_nio_FloatBuffer`               | [java.nio.FloatBuffer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/FloatBuffer.html)                       |
|                                      | `java_nio_DoubleBuffer`              | [java.nio.DoubleBuffer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/DoubleBuffer.html)                     |
| `equality.java_io_charset`           | `java_nio_charset_Charset`           | [java.nio.charset.Charset](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/charset/Charset.html)               |
| `equality.java_io_file`              | `java_nio_file_Path`                 | [java.nio.file.Path](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/file/Path.html)                           |
| `equality.java_seq`                  | `java_sql_Date`                      | [java.sql.Date](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/Date.html)                                      |
|                                      | `java_sql_Time`                      | [java.sql.Time](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/Time.html)                                      |
|                                      | `java_sql_Timestamp`                 | [java.sql.Timestamp](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/Timestamp.html)                            |
|                                      | `java_sql_SQLType `                  | [java.sql.SQLType](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/SQLType.html)                                |
| `equality.java_text`                 | `java_text_Collator`                 | [java.text.Collator](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/text/Collator.html)                           |
|                                      | `java_text_Number Format`            | [java.text.NumberFormat](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/text/NumberFormat.html)                   |
| `equality.java_time`                 | `java_time_Duration`                 | [java.time.Duration](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Duration.html)                           |
|                                      | `java_time_Instant`                  | [java.time.Instant](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Instant.html)                             |
|                                      | `java_time_LocalDate`                | [java.time.LocalDate](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDate.html)                         |
|                                      | `java_time_LocalDateTime`            | [java.time.LocalDateTime](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html)                 |
|                                      | `java_time_LocalTime`                | [java.time.LocalTime](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalTime.html)                         |
|                                      | `java_time_MonthDay`                 | [java.time.MonthDay](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/MonthDay.html)                           |
|                                      | `java_time_OffsetDateTime`           | [java.time.OffsetDateTime](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/OffsetDateTime.html)               |
|                                      | `java_time_OffsetTime`               | [java.time.OffsetTime](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/OffsetTime.html)                       |
|                                      | `java_time_Period`                   | [java.time.Period](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Period.html)                               |
|                                      | `java_time_Year`                     | [java.time.Year](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Year.html)                                   |
|                                      | `java_time_YearMonth`                | [java.time.YearMonth](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/YearMonth.html)                         |
|                                      | `java_time_ZonedDateTime`            | [java.time.ZonedDateTime](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/ZonedDateTime.html)                 |
|                                      | `java_time_ZoneId`                   | [java.time.ZoneId](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/ZoneId.html)                               |
|                                      | `java_time_ZoneOffset`               | [java.time.ZoneOffset](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/ZoneOffset.html)                       |
|                                      | `java_time_DayOfWeek`                | [java.time.DayOfWeek](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/DayOfWeek.html)                         |
|                                      | `java_time_Month`                    | [java.time.Month](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Month.html)                                 |
| `equality.java_util`                 | `java_util_Date`                     | [java.util.Date](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Date.html)                                   |
|                                      | `java_util_Locale`                   | [java.util.Locale](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Locale.html)                               |
|                                      | `java_util_Locale_Category`          | [java.util.Locale.Category](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Locale.Category.html)             |
|                                      | `java_util_Locale_IsoCountryCode`    | [java.util.Locale.IsoCountryCode](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Locale.IsoCountryCode.html) |
|                                      | `java_util_Locale_LanguageRange`     | [java.util.Locale.LanguageRange](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Locale.LanguageRange.html)   |
|                                      | `java_util_Properties`               | [java.util.Properties](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Properties.html)                       |
|                                      | `java_util_TimeZone`                 | [java.util.TimeZone](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/TimeZone.html)                           |
| `equality.scala`                     | `scala_Array`                        | [scala.Array](https://scala-lang.org/api/3.x/scala/Array.html)                                                                       |
| `equality.scala_concurrent_duration` | `scala_concurrent_duration_Duration` | [scala.concurrent.duration.Duration](https://scala-lang.org/api/3.x/scala/concurrent/duration/Duration.html)                         |
|                                      | `scala_concurrent_duration_Deadline` | [scala.concurrent.duration.Deadline](https://scala-lang.org/api/3.x/scala/concurrent/duration/Deadline.html)                         |


### Creating your group of equality type class instances
```scala
package mypackage
import equality.Eq

// all Eq defined for package java.util + other 3 predefined + 1 on your own
object MyEqInstances:

   // top (package) level wildcard exports (.* and .given) are not allowed in Scala, EqInstances.given must be used
   export equality.java_util.EqInstances.given
   
   export equality.java_io_File
   export equality.java_nio_file_Path
   export equality.scala_Array
   
   given Eq[java.util.jar.Attributes] = Eq
```

subsequently, use `MyEqInstances` anywhere with
```scala
import mypackage.MyEqInstances.given
```

## Collection extensions

:warning: **This feature requires enabling strict equality compiler option!**

```scala
import equality.collection.*
import equality.Eq

final case class Apple(x: String) derives Eq
final case class Car(x: String) derives Eq

val (appleA, appleB, appleC) = (Apple("A"), Apple("B"), Apple("C"))
val (carX, carY) = (Car("X"), Car("Y"))

val apples = List(appleA, appleB, appleC)
val cars = List(carX, carY)

// it's pointless to search for a car in a list of apples
apples.contains(carX)
// type checks but it shouldn't --> yields false

apples.contains_safe(carX) 
// ERROR: Values of types A and A cannot be compared with == or !=
//        where: A is a type variable with constraint >: Apple | Car

// it's pointless to remove a list of cars from a list of apples
apples.diff(cars)      
// type checks but it shouldn't --> returns the original apple List

apples.diff_safe(cars)
// ERROR: Values of types Apple and Apple | Car cannot be compared with == or !=
```

As a workaround, until this issue is solved in a consistent way, search & replace those, anywhere the .xxx_safe() signatures do compile with `import equality.collection.*`:

| From                 | To                        |
|----------------------|---------------------------|
| `.contains(`         | `.contains_safe(`         |
| `.containsSlice(`    | `.containsSlice_safe(`    |
| `.indexOf(`          | `.indexOf_safe(`          |
| `.indexOfSlice(`     | `.indexOfSlice_safe(`     |
| `.lastIndexOf(`      | `.lastIndexOf_safe(`      |
| `.lastIndexOfSlice(` | `.lastIndexOfSlice_safe(` |
| `.sameElements(`     | `.sameElements_safe(`     |
| `.search(`           | `.search_safe(`           |
| `.diff(`             | `.diff_safe(`             |
| `.intersect(`        | `.intersect_safe(`        |


The workarounds are defined for collection traits which have a covariant type parameter

```scala
trait Seq[+A]
trait Iterator[+A]
trait IterableOnce[+A]
```

Set and Map are generally safe because they expose an invariant type parameter

```scala
trait Set[A]
trait Map[K, +V] // only the variance of the key is relevant (Map has similar semantics to Set)
```

### Notes
1. With strict equality turned off, `.xxx_safe()` methods behave like the original `.xxx()` signatures
2. The extensions are defined for root collections, mutable collections and immutable collections
3. The collection traits/classes to pay attention typically are `Seq`, `IndexedSeq`, `List`, `LazyList`, `Vector`, `ArraySeq`, `Iterator`, `Queue`  
4. No extension methods are defined for any traits/classes inheriting `Set` or `Map`. Such types have type safe signatures *out of the box*
5. The `.xxx_safe()` methods are inlined, they produce the same code as the original`.xxx()` signatures; the only difference is type safety
6. There are no known debugging difficulties associated with `.xxx_safe()`
7. Hopefully this extension will become obsolete, replaced by some solution that allows to safely use the original collections API

# Strict equality considerations

### No arbitrary comparisons

Excluding some elementary types, strict equality disallows values of the same type from comparing each other with `==` and `!=` *out of the box*.
At first sight this may be seen as an unneeded complication with no benefits, but it provides an additional level of type safety.

For example, it is potentially critical to compare functions with `==`:
```scala
type F = Int => Int
val f1:F = (x:Int) => x + 1
val f2:F = (x:Int) => x + 1

// compiles with strict equality turned off
// returns the same as f1 eq f2 (comparison for reference equality, not object equality)
if f1 == f2 then whatever
```
What is the intention behind `f1 == f2`: to compare reference equality (which should evaluate to `false`), or to compare algorithmical equality (which should evaluate to `true`)? There is currently no way to compare algorithmical equality in Scala, so it could potentially be a programming error. In such a case, in strict equality the compiler can invite to think about it:
```scala
// strict equality on
if f1 == f2 then whatever // ERROR: Values of types F and F cannot be compared with == or !=
```
If the intention is to compare the functions for reference equality, it can be done with `f1 eq f2` (although type unsafe) or by *locally* allowing `f1 == f2` (after realizing `==` boils down to reference equality).
Similar pitfalls may happen for traits `Future`, `Promise`, `ServerSocket` and other structures which are critical to equal *out of the box*.

However, it can become tedious to declare strict equality type class instances for types like `Array`, `LocalDate`, `File`, or `Duration` (either Scala or Java `Duration`), which is the motivation for this library.

### Composition

In the following example, `derives Eq` does allow to compare values of class `Person` for equality, even without `given Eq[LocalDate]` in scope

```scala
import java.time.LocalDate
import equality.Eq

final case class Person(name: String, dayOfBirth: LocalDate) derives Eq
```

### Inheritance

```scala
abstract class Animal
final case class Cat() extends Animal derives Eq
final case class Dog() extends Animal derives Eq
// Values of type Cat can compare each other with == and != 
// Values of type Dog can compare each other with == and != 
// Within this hierarchy, any other comparison with == and != won't type check
```

```scala
abstract class Animal derives Eq
final case class Cat() extends Animal
final case class Dog() extends Animal
// Within this hierarchy, any value can compare to any other value with == and !=
```

### Type parameters
```scala
  final case class Bag[A](elements: A*)

  // propagate Eq[A] --> Eq[Bag[A]]
  given [A](using Eq[A]): Eq[Bag[A]] = Eq

  final case class Apple(x: Int) derives Eq
  final case class Pear(x: Int) // no Eq

  val apples: Bag[Apple] = Bag(Apple(0), Apple(1), Apple(2))
  val pears: Bag[Pear] = Bag(Pear(10), Pear(11), Pear(12))

  apples == apples

  pears == pears
  // ERROR: Values of types Bag[Pear] and Bag[Pear] cannot be compared with == or !=.
  // I found : given_Eq_Bag[Pear](/* missing */ summon[equality.Eq[Pear]])
  // But no implicit values were found that match type equality.Eq[Pear].

  apples == pears
  // ERROR: Values of types Bag[Apple] and Bag[Pear] cannot be compared with == or !=.
  // I found: given_Eq_Bag[A]
  // But given instance given_Eq_Bag does not match type CanEqual[Bag[Apple], Bag[Pear]].
```

### Standard library types
1. Equality for standard types are defined to avoid the most common type safety pitfalls.
2. For example, in `java.time.*`, `Date` and `DateTime` both extend `Temporal`.
   However, no `given Eq[Temporal]` is provided, as it would let `Date` and `DateTime` become comparable with `==` and `!=`.
   This would contraddict the principle of type safety, because `Date` and `DateTime` will always yield false when compared with one another for equality (see their java implementation of `.equals()`).
3. In other words, when the compiler successfully type checks `if myDate == myDateTime then x else y`, the code suggest the expression could indeed in some cirmumstances yield `x`, but it never will: `x` is [dead code](https://en.wikipedia.org/wiki/Dead_code).
   Therefore the imported definitions will make sure this will **not** type check.
4. Conversely, if you need to compare `Temporal` references with `==` and `!=`, you know what you are doing; be aware you need different semantics (this is easily forgotten because we are used to universal equality).
   In such a case, it is appropriate to *locally* define `given Eq[Temporal] = Eq`. For your own benefit, keep this local and stay safe in the rest of your code.
5. Likewise for `Inet6Address` and `Inet4Address`, both implementing `InetAddress`.

### Enforcing strict equality
In order to guarantee the use of strict equality regardless of build settings, you may include this in your sources

```scala
import equality.enforceStrictEquality

// does not need to be called, it fails to compile with strict equality turned off
enforceStrictEquality()
```