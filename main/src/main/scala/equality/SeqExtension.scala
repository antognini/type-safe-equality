package equality

import equality.TypeSafeSeqExtension.*

import scala.collection.Searching.SearchResult
import scala.collection.mutable

private[equality] trait TypeSafeSeqExtension
  extends TypesafeRootSeqExtension
    with TypeSafeRootSeqExtensionForOverloads
    with TypesafeMutableSeqExtension
    with TypesafeImmutableSeqExtension

private[equality] object TypeSafeSeqExtension extends TypeSafeSeqExtension:

  trait TypesafeRootSeqExtension:
    extension[A, S <: collection.Seq[A]] (seq: S)

      /**
       * Equality-safe alternative to the contains() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def contains_eq[B >: A](x: B)
                                    (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.contains(x)

      /**
       * Equality-safe alternative to the containsSlice() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def containsSlice_eq[B >: A](that: collection.Seq[B])
                                         (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.containsSlice(that)

      /**
       * Equality-safe alternative to the endsWith() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def endsWith_eq[B >: A](that: collection.Iterable[B])
                                    (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.endsWith(that)

      /**
       * Equality-safe alternative to the indexOf() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def indexOf_eq[B >: A](x: B)
                                   (using CanEqual[A, B], CanEqual[B, A]): Int = seq.indexOf(x)

      /**
       * Equality-safe alternative to the indexOfSlice() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def indexOfSlice_eq[B >: A](that: collection.Seq[B])
                                        (using CanEqual[A, B], CanEqual[B, A]): Int = seq.indexOfSlice(seq)

      /**
       * Equality-safe alternative to the lastIndexOf() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def lastIndexOf_eq[B >: A](elem: B, end: Int = seq.length - 1)
                                       (using CanEqual[A, B], CanEqual[B, A]): Int = seq.lastIndexOf(elem, end)

      /**
       * Equality-safe alternative to the lastIndexOfSlice() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def lastIndexOfSlice_eq[B >: A](that: collection.Seq[B])
                                            (using CanEqual[A, B], CanEqual[B, A]): Int = seq.lastIndexOfSlice(that)

      /**
       * Equality-safe alternative to the sameElements() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def sameElements_eq[B >: A](that: IterableOnce[B])
                                        (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.sameElements(that)

      /**
       * Equality-safe alternative to the search() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def search_eq[B >: A](elem: B)
                                  (using Ordering[B], CanEqual[A, B], CanEqual[B, A]): SearchResult = seq.search(elem)

      /**
       * Equality-safe alternative to the startsWith() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def startsWith_eq[B >: A](that: IterableOnce[B], offset: Int = 0)
                                      (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.startsWith(that, offset)

  trait TypeSafeRootSeqExtensionForOverloads:
    extension[A, S <: collection.Seq[A]] (seq: S)

      /**
       * Equality-safe alternative to the indexOf() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def indexOf_eq[B >: A](x: B, from: Int)
                                   (using CanEqual[A, B], CanEqual[B, A]): Int = seq.indexOf(x, from)

      /**
       * Equality-safe alternative to the indexOfSlice() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def indexOfSlice_eq[B >: A](that: collection.Seq[B], from: Int)
                                        (using CanEqual[A, B], CanEqual[B, A]): Int = seq.indexOfSlice(seq, from)

      /**
       * Equality-safe alternative to the lastIndexOfSlice() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def lastIndexOfSlice_eq[B >: A](that: collection.Seq[B], end: Int)
                                            (using CanEqual[A, B], CanEqual[B, A]): Int = seq.lastIndexOfSlice(that, end)

      /**
       * Equality-safe alternative to the search() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def search_eq[B >: A](elem: B, from: Int, to: Int)
                                  (using Ordering[B], CanEqual[A, B], CanEqual[B, A]): SearchResult = seq.search(elem, from, to)


  trait TypesafeMutableSeqExtension:
    extension[A] (seq: mutable.Seq[A])

      /**
       * Equality-safe alternative to the  diff() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def diff_eq[B >: A](that: collection.Seq[B])
                                (using CanEqual[A, B], CanEqual[B, A]): mutable.Seq[A] = seq.diff(that)

      /**
       * Equality-safe alternative to the intersect() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def intersect_eq[B >: A](that: collection.Seq[B])
                                     (using CanEqual[A, B], CanEqual[B, A]): mutable.Seq[A] = seq.intersect(that)

  trait TypesafeImmutableSeqExtension:
    extension[A] (seq: Seq[A])

      /**
       * Equality-safe alternative to the diff() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def diff_eq[B >: A](that: collection.Seq[B])
                                (using CanEqual[A, B], CanEqual[B, A]): Seq[A] = seq.diff(that)

      /**
       * Equality-safe alternative to the intersect() method.
       *
       * @see [[https://github.com/antognini/type-safe-equality/tree/main#collection-extensions Library documentation]]
       */
      inline def intersect_eq[B >: A](that: collection.Seq[B])
                                     (using CanEqual[A, B], CanEqual[B, A]): Seq[A] = seq.intersect(that)

end TypeSafeSeqExtension