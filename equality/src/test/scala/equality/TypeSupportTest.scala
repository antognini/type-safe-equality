package equality

import equality.core.TypeSupport.*
import org.scalatest.freespec.AnyFreeSpec


class TypeSupportTest extends AnyFreeSpec:
  "" - {
    "extractTypeParameters" - {

      "1 parameter" - {
        "plain" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[A], bingo.Mill[A]]]".extractTypeParameters == Seq("A")

        "as tuple" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[(A,A)], bingo.Mill[(A,A)]]]".extractTypeParameters == Seq("A")
      }
      "2 parameters" - {
        "plain" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[A], bingo.Mill[B]]]".extractTypeParameters == Seq("A", "B")

        "as tuple" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[(A,B)], bingo.Mill[(A,B)]]]".extractTypeParameters == Seq("A", "B")
      }
      "3 parameters" - {
        "plain" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[C]]]".extractTypeParameters == Seq("A", "B", "C")

        "as tuple" in :
          assert:
            "mypackage.X.Box[other.Z.Bag[bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)]]]".extractTypeParameters == Seq("A", "B", "C")
      }
    }
    "replaceTypeParameterWithWildcard" - {

      "1 parameter" - {
        "plain" in :
          assert:
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[A]]]".wildcardTypeParameter("A") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[?], bingo.Mill[?]]]"

        "as tuple" in :
          assert:
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,A)], bingo.Mill[(A,A)])]]".wildcardTypeParameter("A") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(?,?)], bingo.Mill[(?,?)])]]"
      }
      "2 parameters" - {
        "plain" in :
          assert:
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B]]]".wildcardTypeParameter("A") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[?], bingo.Mill[B]]]"

            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B]]]".wildcardTypeParameter("B") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[?]]]"

        "as tuple" in :
          assert:
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B)], bingo.Mill[(A,B)])]]".wildcardTypeParameter("A") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(?,B)], bingo.Mill[(?,B)])]]"

            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B)], bingo.Mill[(A,B)])]]".wildcardTypeParameter("B") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,?)], bingo.Mill[(A,?)])]]"
      }
      "3 parameters" - {
        "plain" in :
          assert:
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[C]]]".wildcardTypeParameter("A") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[?], bingo.Mill[B], bingo.Mill[C]]]"

            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[C]]]".wildcardTypeParameter("B") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[?], bingo.Mill[C]]]"

            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[C]]]".wildcardTypeParameter("C") ==
            "mypackage.A.Box[other.B.Bag[bingo.Mill[A], bingo.Mill[B], bingo.Mill[?]]]"

        "as tuple" in :
          assert:
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)])]]".wildcardTypeParameter("A") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(?,B,C)], bingo.Mill[(?,B,C)], bingo.Mill[(?,B,C)])]]"

            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)])]]".wildcardTypeParameter("B") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,?,C)], bingo.Mill[(A,?,C)], bingo.Mill[(A,?,C)])]]"

            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)], bingo.Mill[(A,B,C)])]]".wildcardTypeParameter("C") ==
            "mypackage.A.Box[other.B.Bag[(bingo.Mill[(A,B,?)], bingo.Mill[(A,B,?)], bingo.Mill[(A,B,?)])]]"
      }
    }
  }