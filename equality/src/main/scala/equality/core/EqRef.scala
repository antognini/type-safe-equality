package equality.core

import scala.annotation.implicitNotFound

export EqRef.Operators.*

/**
 * Reference equality type class with automatic derivation for final types.
 *
 * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#verifying-reference-equality Library documentation]]
 * @tparam T arbitrary type
 */
@implicitNotFound("References of types ${T} and ${T} cannot be compared with eqRef or neRef")
sealed trait EqRef[-T]

object EqRef:

  /**
   * Creates an EqRef instance for an arbitrary type without verifying equality requirements.
   *
   * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#assuming-reference-equality Library documentation]]
   * @tparam T arbitrary type
   */
  sealed trait assumed[-T] extends EqRef[T]

  /**
   * Creates an EqRef instance for an arbitrary type without verifying equality requirements.
   *
   * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#assuming-reference-equality documentation]]
   */
  object assumed extends assumed[Any]:
    def derived[T]: assumed[T] = assumed

  // FIXME - consider implementing a derivation mechanism
  inline def derived[T]: EqRef[T] = EqRefAny

  object Operators:
    extension[T <: AnyRef: EqRef] (value: T)

      /**
       * Equality-safe alternative to the eq operator.
       *
       * @see [[https://github.com/antognini/type-safe-equality#verifying-reference-equality Library documentation]]
       * @tparam U target type
       * @param otherValue target value to be compared against
       * @return true if the two references are equal, false otherwise
       */
      inline def eqRef[U <: T: EqRef](otherValue: U): Boolean = value eq otherValue

      /**
       * Equality-safe alternative to the ne operator.
       *
       * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#verifying-reference-equality Library documentation]]
       * @tparam U target type
       * @param otherValue target value to be compared against
       * @return true if the two references are not equal, false otherwise
       */
      inline def neRef[U <: T: EqRef](otherValue: U): Boolean = value ne otherValue
    end extension
  end Operators

  /**
   * Enables hybrid equality.
   *
   * This is equivalent to the default behavior of `==` and `!=` operators in Scala but
   * disallowing comparison of values for unrelated or unsupported types.
   */
  object Hybrid:
    /**
     * Creates an Eq instance equivalent to this EqRef instance.
     *
     * This has the following additional effects:
     * - `==` and `!=` operators can also compare references if given EqRef instance is in scope for the compared type.
     * - Eq derivation mechanism for product types supports fields with EqRef instances.
     *
     * @tparam T arbitrary type
     * @return Eq instance equivalent to the this EqRef instance
     */
    given eqRefToEq[T: EqRef]: Eq[T] = Eq.assumed
  end Hybrid

  private object EqRefAny extends EqRef[Any]
end EqRef
