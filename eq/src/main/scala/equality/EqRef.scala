package equality

import equality.Eq


/**
 * Equality-safe reference comparison.
 *
 * @see [[https://github.com/antognini/type-safe-equality/tree/main#reference-equality Library documentation]]
 */
sealed trait EqRef[A <: AnyRef](
  private val a: Option[A]
) derives Eq.assumed:

  extension (a: A)

    /**
     * Equality-safe alternative to the eq operator.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#reference-equality Library documentation]]
     * @tparam B target type
     * @param value target value to be compared against
     * @return true if the two references are equal, false otherwise
     */
    inline def equalRef[B <: A](value: B): Boolean = a eq value

    /**
     * Equality-safe alternative to the ne operator.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#reference-equality Library documentation]]
     * @tparam B target type
     * @param value target value to be compared against
     * @return true if the two references are not equal, false otherwise
     */
    inline def notEqualRef[B <: A](value: B): Boolean = a ne value

  end extension

  def get: A = a.get

  override def equals(any: Any): Boolean =
    (any : @unchecked) match
      case that: EqRef[A] =>
        (this.a, that.a) match
          case (Some(thisRef), Some(thatRef)) => thisRef eq thatRef
          case _ => false
      case _ => false

  override def toString: String =
    a match
      case Some(value) =>
        val identity = System.identityHashCode(value)
        s"EqRef($identity -> $value)"
      case _ =>
        s"EqRef"

end EqRef

object EqRef:
  def apply[A <: AnyRef]: EqRef[A] = new EqRef[A](None){}
  def apply[A <: AnyRef](a: A): EqRef[A] = new EqRef[A](Some(a)){}
end EqRef
