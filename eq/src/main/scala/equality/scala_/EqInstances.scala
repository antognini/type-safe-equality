package equality.scala_

import equality.Eq

export EqInstances.given

type AnyNumber = Byte | Char | Short | Int | Long | BigInt | Float | Double | BigDecimal

type AnyJavaNumber = java.lang.Byte     | java.lang.Character   | java.lang.Short       |
                     java.lang.Integer  | java.lang.Long        | java.math.BigInteger  |
                     java.lang.Float    | java.lang.Double      | java.math.BigDecimal

object EqInstances:

  given scala_AnyNumber: Eq[AnyNumber] = Eq.assumed
  given scala_AnyJavaNumber: Eq[AnyJavaNumber] = Eq.assumed
  given scala_AnyScalaOrJavaNumber: Eq[AnyNumber | AnyJavaNumber] = Eq.assumed

  given scala_Array[T: Eq]: Eq[Array[T]] = Eq.assumed
  given scala_Boolean: Eq[Boolean] = Eq.assumed
  given scala_Byte: Eq[Byte] = Eq.assumed
  given scala_Char: Eq[Char] = Eq.assumed
  given scala_Double: Eq[Double] = Eq.assumed
  given scala_Float: Eq[Float] = Eq.assumed
  given scala_Int: Eq[Int] = Eq.assumed
  given scala_Long: Eq[Long] = Eq.assumed
  given scala_Nothing: Eq[Nothing] = Eq.assumed
  given scala_Null: Eq[Null] = Eq.assumed
  given scala_Option[A: Eq]: Eq[Option[A]] = Eq.assumed
  given scala_Short: Eq[Short] = Eq.assumed
  given scala_String: Eq[String] = Eq.assumed
  given scala_Unit: Eq[Unit] = Eq.assumed
  
  given scala_Tuple0: Eq[EmptyTuple] = Eq.assumed
  given scala_Tuple1[T1: Eq]: Eq[T1 *: EmptyTuple] = Eq.assumed
  given scala_Tuple2[T1: Eq, T2: Eq]: Eq[T1 *: T2 *: EmptyTuple] = Eq.assumed
  given scala_Tuple3[T1: Eq, T2: Eq, T3: Eq]: Eq[T1 *: T2 *: T3 *: EmptyTuple] = Eq.assumed
  given scala_Tuple4[T1: Eq, T2: Eq, T3: Eq, T4: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: EmptyTuple] = Eq.assumed
  given scala_Tuple5[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: EmptyTuple] = Eq.assumed
  given scala_Tuple6[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: EmptyTuple] = Eq.assumed
  given scala_Tuple7[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: EmptyTuple] = Eq.assumed
  given scala_Tuple8[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: EmptyTuple] = Eq.assumed
  given scala_Tuple9[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: EmptyTuple] = Eq.assumed
  given scala_Tuple10[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: EmptyTuple] = Eq.assumed
  given scala_Tuple11[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: EmptyTuple] = Eq.assumed
  given scala_Tuple12[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: EmptyTuple] = Eq.assumed
  given scala_Tuple13[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: EmptyTuple] = Eq.assumed
  given scala_Tuple14[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: EmptyTuple] = Eq.assumed
  given scala_Tuple15[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: EmptyTuple] = Eq.assumed
  given scala_Tuple16[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: EmptyTuple] = Eq.assumed
  given scala_Tuple17[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: EmptyTuple] = Eq.assumed
  given scala_Tuple18[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: EmptyTuple] = Eq.assumed
  given scala_Tuple19[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: EmptyTuple] = Eq.assumed
  given scala_Tuple20[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: EmptyTuple] = Eq.assumed
  given scala_Tuple21[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: EmptyTuple] = Eq.assumed
  given scala_Tuple22[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: EmptyTuple] = Eq.assumed
  given scala_Tuple23[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: EmptyTuple] = Eq.assumed
  given scala_Tuple24[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq, T24: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: T24 *: EmptyTuple] = Eq.assumed
  given scala_Tuple25[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq, T24: Eq, T25: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: T24 *: T25 *: EmptyTuple] = Eq.assumed
  given scala_Tuple26[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq, T24: Eq, T25: Eq, T26: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: T24 *: T25 *: T26 *: EmptyTuple] = Eq.assumed
  given scala_Tuple27[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq, T24: Eq, T25: Eq, T26: Eq, T27: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: T24 *: T25 *: T26 *: T27 *: EmptyTuple] = Eq.assumed
  given scala_Tuple28[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq, T24: Eq, T25: Eq, T26: Eq, T27: Eq, T28: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: T24 *: T25 *: T26 *: T27 *: T28 *: EmptyTuple] = Eq.assumed
  given scala_Tuple29[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq, T24: Eq, T25: Eq, T26: Eq, T27: Eq, T28: Eq, T29: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: T24 *: T25 *: T26 *: T27 *: T28 *: T29 *: EmptyTuple] = Eq.assumed
  given scala_Tuple30[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq, T24: Eq, T25: Eq, T26: Eq, T27: Eq, T28: Eq, T29: Eq, T30: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: T24 *: T25 *: T26 *: T27 *: T28 *: T29 *: T30 *: EmptyTuple] = Eq.assumed
  given scala_Tuple31[T1: Eq, T2: Eq, T3: Eq, T4: Eq, T5: Eq, T6: Eq, T7: Eq, T8: Eq, T9: Eq, T10: Eq, T11: Eq, T12: Eq, T13: Eq, T14: Eq, T15: Eq, T16: Eq, T17: Eq, T18: Eq, T19: Eq, T20: Eq, T21: Eq, T22: Eq, T23: Eq, T24: Eq, T25: Eq, T26: Eq, T27: Eq, T28: Eq, T29: Eq, T30: Eq, T31: Eq]: Eq[T1 *: T2 *: T3 *: T4 *: T5 *: T6 *: T7 *: T8 *: T9 *: T10 *: T11 *: T12 *: T13 *: T14 *: T15 *: T16 *: T17 *: T18 *: T19 *: T20 *: T21 *: T22 *: T23 *: T24 *: T25 *: T26 *: T27 *: T28 *: T29 *: T30 *: T31 *: EmptyTuple] = Eq.assumed
