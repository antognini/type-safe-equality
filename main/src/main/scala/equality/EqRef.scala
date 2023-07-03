package equality

sealed trait EqRef[A <: AnyRef]:

  extension (a: A)
    inline def equalRef[B <: A](b: B): Boolean = a eq b
    inline def notEqualRef[B <: A](b: B): Boolean = a ne b

  inline def equalRef[B <: A](a: A, b: B): Boolean = a eq b
  inline def notEqualRef[B <: A](a: A, b: B): Boolean = a ne b

object EqRef:
  def apply[A <: AnyRef]: EqRef[A] = new EqRef[A]{}
