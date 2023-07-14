package equality

import annotation.implicitNotFound

export Eq.given

// Marker trait for unit tests
private[equality] trait EqTest

/**
 * Strict equality type class with automatic derivation for `Product` types.
 *
 * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#eq-type-class Library documentation]]
 * @tparam T `Product` type
 */

@implicitNotFound("Values of types ${T} and ${T} cannot be compared with == or !=")
sealed trait Eq[-T]:
  /**
   *  For testing only
   */
  private[equality] val violations: Seq[String] = Nil

object Eq:
  given eq_from_assumed[A: Eq.assumed]: Eq[A] = Instance
  given eq_CanEqual[T: Eq]: CanEqual[T, T] = CanEqual.derived


  /**
   * Creates an Eq instance for an arbitrary type without verifying equality requirements.
   *
   * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#assuming-equality Library documentation]]
   */
  object assumed extends assumed[Any]:
    def derived[T]: assumed[T] = assumed

  /**
   * Creates an Eq instance for an arbitrary type without verifying equality requirements.
   *
   * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#assuming-equality Library documentation]]
   * @tparam T arbitrary type
   */
  sealed trait assumed[-T] extends Eq[T]

  /**
   * Creates an Eq instance for a `Product` type while verifying equality requirements.
   *
   * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#verifying-equality Library documentation]]
   * @tparam T `Product` type
   * @return Eq instance for the specified type
   */
  inline def derived[T]: Eq[T] = EqMacro.derived


  /**
   * Identity function that requires an Eq type class instance for the passed argument.
   *
   * @see [[https://github.com/antognini/type-safe-equality/blob/main/README.md#assuming-equality Library documentation]]
   * @tparam T arbitrary type
   */
  def apply[T: Eq](value: T): T = value
  
  private object Instance extends Eq[Any]

  private[equality] def apply[T]: Eq[T] = Instance


  // For testing only
  private[equality] def apply[T](violationSeq: Seq[String]): Eq[T] =
    new Eq[T]:
      override val violations = violationSeq

end Eq