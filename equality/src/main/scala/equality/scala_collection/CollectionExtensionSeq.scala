package equality.scala_collection

import equality.core.Eq

import scala.collection.Searching.SearchResult
import scala.collection.mutable

trait CollectionExtensionSeq:
  extension[A, S <: collection.Seq[A]] (seq: S)

    /**
     * Equality-safe alternative to the contains() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def contains_eq[B >: A](elem: B)
                                  (using Eq[B]): Boolean = seq.contains(elem)

    /**
     * Equality-safe alternative to the containsSlice() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def containsSlice_eq[B >: A](that: collection.Seq[B])
                                       (using Eq[B]): Boolean = seq.containsSlice(that)

    /**
     * Equality-safe alternative to the endsWith() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def endsWith_eq[B >: A](that: collection.Iterable[B])
                                  (using Eq[B]): Boolean = seq.endsWith(that)

    /**
     * Equality-safe alternative to the indexOf() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def indexOf_eq[B >: A](elem: B)
                                 (using Eq[B]): Int = seq.indexOf(elem)

    /**
     * Equality-safe alternative to the indexOfSlice() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def indexOfSlice_eq[B >: A](that: collection.Seq[B])
                                      (using Eq[B]): Int = seq.indexOfSlice(seq)

    /**
     * Equality-safe alternative to the lastIndexOf() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def lastIndexOf_eq[B >: A](elem: B, end: Int = seq.length - 1)
                                     (using Eq[B]): Int = seq.lastIndexOf(elem, end)

    /**
     * Equality-safe alternative to the lastIndexOfSlice() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def lastIndexOfSlice_eq[B >: A](that: collection.Seq[B])
                                          (using Eq[B]): Int = seq.lastIndexOfSlice(that)

    /**
     * Equality-safe alternative to the sameElements() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def sameElements_eq[B >: A](that: IterableOnce[B])
                                      (using Eq[B]): Boolean = seq.sameElements(that)

    /**
     * Equality-safe alternative to the search() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def search_eq[B >: A](elem: B)
                                (using Eq[B], Ordering[B]): SearchResult = seq.search(elem)

    /**
     * Equality-safe alternative to the startsWith() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def startsWith_eq[B >: A](that: IterableOnce[B], offset: Int = 0)
                                    (using Eq[B]): Boolean = seq.startsWith(that, offset)
  end extension
end CollectionExtensionSeq


trait CollectionExtensionSeqOverload:
  extension[A, S <: collection.Seq[A]] (seq: S)

    /**
     * Equality-safe alternative to the indexOf() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def indexOf_eq[B >: A](elem: B, from: Int)
                                 (using Eq[B]): Int = seq.indexOf(elem, from)

    /**
     * Equality-safe alternative to the indexOfSlice() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def indexOfSlice_eq[B >: A](that: collection.Seq[B], from: Int)
                                      (using Eq[B]): Int = seq.indexOfSlice(seq, from)

    /**
     * Equality-safe alternative to the lastIndexOfSlice() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def lastIndexOfSlice_eq[B >: A](that: collection.Seq[B], end: Int)
                                          (using Eq[B]): Int = seq.lastIndexOfSlice(that, end)

    /**
     * Equality-safe alternative to the search() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def search_eq[B >: A](elem: B, from: Int, to: Int)
                                (using Ordering[B], Eq[B]): SearchResult = seq.search(elem, from, to)

//  end extension
end CollectionExtensionSeqOverload


trait CollectionExtensionSeqMutable:
  extension[A] (seq: mutable.Seq[A])

    /**
     * Equality-safe alternative to the  diff() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def diff_eq[B >: A](that: collection.Seq[B])
                              (using Eq[B]): mutable.Seq[A] = seq.diff(that)

    /**
     * Equality-safe alternative to the intersect() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def intersect_eq[B >: A](that: collection.Seq[B])
                                   (using Eq[B]): mutable.Seq[A] = seq.intersect(that)

  end extension
end CollectionExtensionSeqMutable


trait CollectionExtensionSeqImmutable:
  extension[A] (seq: Seq[A])

    /**
     * Equality-safe alternative to the diff() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def diff_eq[B >: A](that: collection.Seq[B])
                              (using Eq[B]): Seq[A] = seq.diff(that)

    /**
     * Equality-safe alternative to the intersect() method.
     *
     * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
     */
    inline def intersect_eq[B >: A](that: collection.Seq[B])
                                   (using Eq[B]): Seq[A] = seq.intersect(that)

  end extension
end CollectionExtensionSeqImmutable
