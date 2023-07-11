package equality.scala_collection

import equality.Eq
import TypeSafeIteratorExtension.*

private[equality] trait TypeSafeIteratorExtension
  extends TypeSafeRootIteratorExtension
    with TypesafeRootIteratorExtensionForOverloads

private[equality] object TypeSafeIteratorExtension extends TypeSafeIteratorExtension:

  trait TypeSafeRootIteratorExtension:
    extension[A, S <: collection.Iterator[A]] (iterator: S)

      /**
       * Equality-safe alternative to the contains() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def contains_eq[B >: A](x: B)
                                    (using Eq[B]): Boolean = iterator.contains(x)


      /**
       * Equality-safe alternative to the indexOf() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def indexOf_eq[B >: A](x: B)
                                   (using Eq[B]): Int = iterator.indexOf(x)

      /**
       * Equality-safe alternative to the sameElements() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def sameElements_eq[B >: A](that: IterableOnce[B])
                                        (using Eq[B]): Boolean = iterator.sameElements(that)

  trait TypesafeRootIteratorExtensionForOverloads:
    extension[A, S <: collection.Iterator[A]] (iterator: S)

      /**
       * Equality-safe alternative to the indexOf method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def indexOf_eq[B >: A](x: B, from: Int)
                                   (using Eq[B]): Int = iterator.indexOf(x, from)

end TypeSafeIteratorExtension
