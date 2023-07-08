package equality

import equality.{*, given}
import org.scalatest.freespec.AnyFreeSpec
import TypeNameUtil.nameOf

class DryRunTest extends AnyFreeSpec:

  "" - {

    "GenericClass" - {
      class GenericClass extends EqTest derives Eq

      inspect[GenericClass](
        "GenericClass".notProduct
      )
    }

    s"Plain0" - {
      case class Plain0()

      case class TestPlain0(x: Plain0) extends EqTest derives Eq

      inspect[TestPlain0](
        "Plain0".cannotCompare
      )

      s"Curried" - {
        case class Curried[A](x: Int)(y: A, z: Plain0) extends EqTest derives Eq

        inspect[Curried[Int]]()
      }
    }

    "Derived0" - {
      case class Derived0() derives Eq

      case class TestDerived0(x: Derived0) extends EqTest derives Eq

      inspect[TestDerived0]()
    }

    "Plain1" - {
      case class Plain1[A](a: A)

      case class TestPlain1A(x: Plain1[Int]) extends EqTest derives Eq
      case class TestPlain1B[A: Eq](x: Plain1[A]) extends EqTest derives Eq
      case class TestPlain1C[A](x: Plain1[A]) extends EqTest derives Eq

      inspect[TestPlain1A](
        "Plain1[Int]".cannotCompare
      )

      inspect[TestPlain1B[Int]](
        "Plain1[?]".cannotCompare
      )

      inspect[TestPlain1C[Int]](
        "Plain1[?]".cannotCompare,
        "A".noContextBound
      )
    }

    "Derived1" - {
      case class Derived1[A: Eq](a: A) derives Eq

      case class TestDerived1A(x: Derived1[Int]) extends EqTest derives Eq
      case class TestDerived1B[A: Eq](x: Derived1[A]) extends EqTest derives Eq
      case class TestDerived1C[A](x: Derived1[A]) extends EqTest derives Eq

      inspect[TestDerived1A]()

      inspect[TestDerived1B[Int]]()

      inspect[TestDerived1C[Int]](
        "A".noContextBound
      )
    }

    "Plain2" - {
      case class Plain2[A, B](a: A, b: B)

      case class TestPlain2A(x: Plain2[Int, String]) extends EqTest derives Eq
      case class TestPlain2B[A: Eq, B: Eq](x: Plain2[A, String], y: Plain2[Int, B]) extends EqTest derives Eq
      case class TestPlain2C[A, B: Eq](x: Plain2[A, String], y: Plain2[Int, B]) extends EqTest derives Eq
      case class TestPlain2D[A, B](x: Plain2[A, String], y: Plain2[Int, B]) extends EqTest derives Eq

      inspect[TestPlain2A](
        "Plain2[Int, String]".cannotCompare
      )

      inspect[TestPlain2B[Int, String]](
        "Plain2[?, String]".cannotCompare,
        "Plain2[Int, ?]".cannotCompare
      )

      inspect[TestPlain2C[Int, String]](
        "Plain2[?, String]".cannotCompare,
        "Plain2[Int, ?]".cannotCompare,
        "A".noContextBound
      )

      inspect[TestPlain2D[Int, String]](
        "Plain2[?, String]".cannotCompare,
        "Plain2[Int, ?]".cannotCompare,
        "A".noContextBound,
        "B".noContextBound
      )
    }

    "Derived2" - {
      case class Derived2[A: Eq, B: Eq](a: A, b: B) derives Eq

      case class TestDerived2A(x: Derived2[Int, String]) extends EqTest derives Eq
      case class TestDerived2B[A: Eq, B: Eq](x: Derived2[A, String], y: Derived2[Int, B]) extends EqTest derives Eq
      case class TestDerived2C[A, B: Eq](x: Derived2[A, String], y: Derived2[Int, B]) extends EqTest derives Eq
      case class TestDerived2D[A, B](x: Derived2[A, String], y: Derived2[Int, B]) extends EqTest derives Eq

      inspect[TestDerived2A]()

      inspect[TestDerived2B[Int, String]]()

      inspect[TestDerived2C[Int, String]](
        "A".noContextBound
      )

      inspect[TestDerived2D[Int, String]](
        "A".noContextBound,
        "B".noContextBound
      )
    }

    s"Complex" - {
      case class Plain1[A](a: A)
      case class Plain2[A, B](a: A, b: B)
      case class Derived2[A: Eq, B: Eq](a: A, b: B) derives Eq

      case class Complex[
        A: Eq,
        B,
        C,
        D,
        E <: AnyVal,
        F,
        G <: B](
                 x: Derived2[A, B],
                 y: Map[Plain1[C], Plain1[D]],
                 e: E,
                 seq: Seq[Plain2[Int, F]]
               ) extends EqTest derives Eq
      inspect[
        Complex[Int, String, Int, String, Boolean, Double | BigDecimal, String]
      ](
        "Map[Plain1[?], Plain1[?]]".cannotCompare,
        "Seq[Plain2[Int, ?]]".cannotCompare,
        "B".noContextBound,
        "C".noContextBound,
        "D".noContextBound,
        "E".noContextBound,
        "F".noContextBound
      )
    }

    s"Speci4lComplex" - {
      case class `Speci4lPlain1`[`*`](a: `*`)
      case class `Speci4lPlain2`[`+`, `@`](a: `+`, b: `@`)
      case class `Speci4lDerived2`[`=`: Eq, `^`: Eq](a: `=`, b: `^`) derives Eq
      
      case class `Speci4lComplex`[
        `#`: Eq,
        `&`,
        `%`,
        `~`,
        `-` <: AnyVal,
        `/`,
        G <: `&`](
                   x: `Speci4lDerived2`[`#`, `&`],
                   y: Map[`Speci4lPlain1`[`%`], `Speci4lPlain1`[`~`]],
                   e: `-`,
                   seq: Seq[`Speci4lPlain2`[Int, `/`]]
                 ) extends EqTest derives Eq
      inspect[
        `Speci4lComplex`[Int, String, Int, String, Boolean, Double | BigDecimal, String],
      ](
        "Map[Speci4lPlain1[?], Speci4lPlain1[?]]".cannotCompare,
        "Seq[Speci4lPlain2[Int, ?]]".cannotCompare,
        "&".noContextBound,
        "%".noContextBound,
        "~".noContextBound,
        "-".noContextBound,
        "/".noContextBound
      )
    }
  }

  inline def inspect[T <: EqTest : Eq](expectedViolations: String*): Unit =
    inspect(nameOf[T], expectedViolations.toSeq)


  def inspect[T <: EqTest : Eq](typeName: String,
                                expectedViolations: Seq[String]): Unit =

    s"Eq[$typeName]" in :
      val violations = summon[Eq[T]].violations
      assert(violations === expectedViolations)


  extension (tpe: String)
    def notProduct =
      s"$tpe is not a product type"

    def noContextBound =
      s"Neither context bound [$tpe: Eq] nor constructor parameter (using Eq[$tpe]) is defined"

    def cannotCompare =
      s"Values of types $tpe and $tpe cannot be compared with == or !="

end DryRunTest