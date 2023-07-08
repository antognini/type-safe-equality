package equality

/**
 * Equality-safe reference comparison.
 *
 * @see [[https://github.com/antognini/type-safe-equality/tree/main#reference-equality Library documentation]]
 */
sealed trait EqRef[A <: AnyRef]:

  extension (a: A)

    /**
     * Equality-safe alternative to the eq operator.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#reference-equality Library documentation]]
     *
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

object EqRef:
  def apply[A <: AnyRef]: EqRef[A] = new EqRef[A] {}
