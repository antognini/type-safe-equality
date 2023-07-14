package equality.scala_collection

import equality.Eq

trait CollectionExtensionIterator:
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

  end extension
end CollectionExtensionIterator

trait CollectionExtensionIteratorOverload:
  extension[A, S <: collection.Iterator[A]] (iterator: S)

    /**
     * Equality-safe alternative to the indexOf method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def indexOf_eq[B >: A](x: B, from: Int)
                                 (using Eq[B]): Int = iterator.indexOf(x, from)

  end extension
end CollectionExtensionIteratorOverload
