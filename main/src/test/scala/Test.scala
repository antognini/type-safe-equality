package test

import equality.scala_.EqInstances.AnyJavaNumber
import org.scalatest.freespec.AnyFreeSpec
import equality.{*, given}

case class Box[+A: Eq](a: A) derives Eq

class CompileTest extends AnyFreeSpec:
  "compile test" in {

    summon[Eq[Byte]]
    summon[Eq[Short]]
    summon[Eq[Char]]
    summon[Eq[Int]]
    summon[Eq[Long]]
    summon[Eq[BigInt]]
    summon[Eq[Float]]
    summon[Eq[Double]]
    summon[Eq[BigDecimal]]
    summon[Eq[String]]
    summon[Eq[Boolean]]
    summon[Eq[Unit]]
    summon[Eq[Null]]
    summon[Eq[Nothing]]
    summon[Eq[Number]]
    summon[Eq[AnyNumber]]
    summon[Eq[AnyJavaNumber]]
    summon[Eq[Int | Long]]
    summon[Eq[Seq[Some[(Boolean, Char, Int)]]]]
    summon[Eq[Seq[Some[(Boolean, Char, Int | Long)]]]]

    summon[Eq[Box[Byte]]]
    summon[Eq[Box[Short]]]
    summon[Eq[Box[Char]]]
    summon[Eq[Box[Int]]]
    summon[Eq[Box[Long]]]
    summon[Eq[Box[BigInt]]]
    summon[Eq[Box[Float]]]
    summon[Eq[Box[Double]]]
    summon[Eq[Box[BigDecimal]]]
    summon[Eq[Box[String]]]
    summon[Eq[Box[Boolean]]]
    summon[Eq[Box[Unit]]]
    summon[Eq[Box[Null]]]
    summon[Eq[Box[Nothing]]]
    summon[Eq[Box[Number]]]
    summon[Eq[Box[AnyNumber]]]
    summon[Eq[Box[AnyJavaNumber]]]
    summon[Eq[Box[Int | Long]]]
    summon[Eq[Box[Seq[Some[(Boolean, Char, Int)]]]]]
    summon[Eq[Box[Seq[Some[(Boolean, Char, Int | Long)]]]]]
  }

