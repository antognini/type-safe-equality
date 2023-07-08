package equality

import equality.TypeParameterSupport.*
import org.scalatest.freespec.AnyFreeSpec


class TypeParameterSupportTest extends AnyFreeSpec:
  "" - {
    "extractTypeParameters" - {

      "1 parameter" - {
        "plain" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[A], bingo.Mill[A]]]".extractTypeParams == Seq("A")

        "as tuple" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[(A,A)], bingo.Mill[(A,A)]]]".extractTypeParams == Seq("A")
      }
      "2 parameters" - {
        "plain" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[A], bingo.Mill[B]]]".extractTypeParams == Seq("A", "B")

        "as tuple" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[(A,B)], bingo.Mill[(A,B)]]]".extractTypeParams == Seq("A", "B")
      }
      "3 parameters" - {
        "plain" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[C]]]".extractTypeParams == Seq("A", "B", "C")

        "as tuple" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)]]]".extractTypeParams == Seq("A", "B", "C")
      }
    }
    "replaceTypeParameterWithWildcard" - {

      "1 parameter" - {
        "plain" in :
          assert:
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[A]]]".replaceTypeParameterWithWildcard("A") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[?], bingo.Mill[?]]]"

        "as tuple" in :
          assert:
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,A)], bingo.Mill[(A,A)])]]".replaceTypeParameterWithWildcard("A") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(?,?)], bingo.Mill[(?,?)])]]"
      }
      "2 parameters" - {
        "plain" in :
          assert:
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B]]]".replaceTypeParameterWithWildcard("A") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[?], bingo.Mill[B]]]"

            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B]]]".replaceTypeParameterWithWildcard("B") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[?]]]"

        "as tuple" in :
          assert:
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B)], bingo.Mill[(A,B)])]]".replaceTypeParameterWithWildcard("A") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(?,B)], bingo.Mill[(?,B)])]]"

            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B)], bingo.Mill[(A,B)])]]".replaceTypeParameterWithWildcard("B") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,?)], bingo.Mill[(A,?)])]]"
      }
      "3 parameters" - {
        "plain" in :
          assert:
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[C]]]".replaceTypeParameterWithWildcard("A") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[?], bingo.Mill[B], bingo.Mill[C]]]"

            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[C]]]".replaceTypeParameterWithWildcard("B") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[?], bingo.Mill[C]]]"

            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[C]]]".replaceTypeParameterWithWildcard("C") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[?]]]"

        "as tuple" in :
          assert:
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)])]]".replaceTypeParameterWithWildcard("A") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(?,B,C)], bingo.Mill[(?,B,C)], bingo.Mill[(?,B,C)])]]"

            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)])]]".replaceTypeParameterWithWildcard("B") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,?,C)], bingo.Mill[(A,?,C)], bingo.Mill[(A,?,C)])]]"

            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)])]]".replaceTypeParameterWithWildcard("C") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B,?)], bingo.Mill[(A,B,?)], bingo.Mill[(A,B,?)])]]"
      }
    }
  }
