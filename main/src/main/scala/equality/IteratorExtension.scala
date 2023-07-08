package equality

import equality.TypeSafeIteratorExtension.*

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
                                    (using CanEqual[A, B], CanEqual[B, A]): Boolean = iterator.contains(x)


      /**
       * Equality-safe alternative to the indexOf() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def indexOf_eq[B >: A](x: B)
                                   (using CanEqual[A, B], CanEqual[B, A]): Int = iterator.indexOf(x)

      /**
       * Equality-safe alternative to the sameElements() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def sameElements_eq[B >: A](that: IterableOnce[B])
                                        (using CanEqual[A, B], CanEqual[B, A]): Boolean = iterator.sameElements(that)

  trait TypesafeRootIteratorExtensionForOverloads:
    extension[A, S <: collection.Iterator[A]] (iterator: S)

      /**
       * Equality-safe alternative to the indexOf method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def indexOf_eq[B >: A](x: B, from: Int)
                                   (using CanEqual[A, B], CanEqual[B, A]): Int = iterator.indexOf(x, from)

end TypeSafeIteratorExtension
