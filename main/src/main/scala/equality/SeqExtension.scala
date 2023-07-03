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
      inline def contains_safe[B >: A](x: B)
                                      (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.contains(x)

      inline def containsSlice_safe[B >: A](that: collection.Seq[B])
                                           (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.containsSlice(that)

      inline def endsWith_safe[B >: A](that: collection.Iterable[B])
                                      (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.endsWith(that)

      inline def indexOf_safe[B >: A](x: B)
                                     (using CanEqual[A, B], CanEqual[B, A]): Int = seq.indexOf(x)

      inline def indexOfSlice_safe[B >: A](that: collection.Seq[B])
                                          (using CanEqual[A, B], CanEqual[B, A]): Int = seq.indexOfSlice(seq)

      inline def lastIndexOf_safe[B >: A](elem: B, end: Int = seq.length - 1)
                                         (using CanEqual[A, B], CanEqual[B, A]): Int = seq.lastIndexOf(elem, end)

      inline def lastIndexOfSlice_safe[B >: A](that: collection.Seq[B])
                                              (using CanEqual[A, B], CanEqual[B, A]): Int = seq.lastIndexOfSlice(that)

      inline def sameElements_safe[B >: A](that: IterableOnce[B])
                                          (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.sameElements(that)

      inline def search_safe[B >: A](elem: B)
                                    (using Ordering[B], CanEqual[A, B], CanEqual[B, A]): SearchResult = seq.search(elem)

      inline def startsWith_safe[B >: A](that: IterableOnce[B], offset: Int = 0)
                                        (using CanEqual[A, B], CanEqual[B, A]): Boolean = seq.startsWith(that, offset)

  trait TypeSafeRootSeqExtensionForOverloads:
    extension[A, S <: collection.Seq[A]] (seq: S)

      inline def indexOf_safe[B >: A](x: B, from: Int)
                                     (using CanEqual[A, B], CanEqual[B, A]): Int = seq.indexOf(x, from)

      inline def indexOfSlice_safe[B >: A](that: collection.Seq[B], from: Int)
                                          (using CanEqual[A, B], CanEqual[B, A]): Int = seq.indexOfSlice(seq, from)

      inline def lastIndexOfSlice_safe[B >: A](that: collection.Seq[B], end: Int)
                                              (using CanEqual[A, B], CanEqual[B, A]): Int = seq.lastIndexOfSlice(that, end)

      inline def search_safe[B >: A](elem: B, from: Int, to: Int)
                                    (using Ordering[B], CanEqual[A, B], CanEqual[B, A]): SearchResult = seq.search(elem, from, to)


  trait TypesafeMutableSeqExtension:
    extension[A] (seq: mutable.Seq[A])
      inline def diff_safe[B >: A](that: collection.Seq[B])
                                  (using CanEqual[A, B], CanEqual[B, A]): mutable.Seq[A] = seq.diff(that)

      inline def intersect_safe[B >: A](that: collection.Seq[B])
                                       (using CanEqual[A, B], CanEqual[B, A]): mutable.Seq[A] = seq.intersect(that)

  trait TypesafeImmutableSeqExtension:
    extension[A] (seq: Seq[A])
      inline def diff_safe[B >: A](that: collection.Seq[B])
                                  (using CanEqual[A, B], CanEqual[B, A]): Seq[A] = seq.diff(that)

      inline def intersect_safe[B >: A](that: collection.Seq[B])
                                       (using CanEqual[A, B], CanEqual[B, A]): Seq[A] = seq.intersect(that)

end TypeSafeSeqExtension