package equality.scala_collection

import equality.core.Eq

trait CollectionExtensionIterator:
  extension[A, I <: collection.Iterator[A]] (iterator: I)

    /**
     * Equality-safe alternative to the contains() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def contains_eq[B >: A](elem: B)
                                  (using Eq[B]): Boolean = iterator.contains(elem)


    /**
     * Equality-safe alternative to the indexOf() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def indexOf_eq[B >: A](elem: B)
                                 (using Eq[B]): Int = iterator.indexOf(elem)

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
  extension[A, I <: collection.Iterator[A]] (iterator: I)

    /**
     * Equality-safe alternative to the indexOf method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def indexOf_eq[B >: A](elem: B, from: Int)
                                 (using Eq[B]): Int = iterator.indexOf(elem, from)

  end extension
end CollectionExtensionIteratorOverload
