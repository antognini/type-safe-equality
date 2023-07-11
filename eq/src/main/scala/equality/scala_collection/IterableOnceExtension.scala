package equality.scala_collection

import equality.Eq

import TypesafeIterableOnceExtension.*

private[equality] trait TypesafeIterableOnceExtension
  extends TypesafeRootIterableOnceExtension

private[equality] object TypesafeIterableOnceExtension extends TypesafeIterableOnceExtension:

  trait TypesafeRootIterableOnceExtension:
    extension[A, I <: collection.IterableOnce[A]] (iterableOnce: I)

      /**
       * Equality-safe alternative to the sameElements() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      @deprecated("Use .iterator.sameElements_eq instead", "2.13.0")
      inline def sameElements_eq[B >: A](that: IterableOnce[B])
                                        (using Eq[B]): Boolean = iterableOnce.sameElements(that)

end TypesafeIterableOnceExtension