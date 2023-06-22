package equality

import equality.TypesafeIteratorExtension.*

trait TypesafeIteratorExtension
  extends TypesafeRootIteratorExtension
    with TypesafeRootIteratorExtensionForOverloads

case object TypesafeIteratorExtension extends TypesafeIteratorExtension:

  trait TypesafeRootIteratorExtension:
    extension[A, S <: collection.Iterator[A]] (iterator: S)
      inline def contains_safe[B >: A](x: B)
                                      (using CanEqual[A, B], CanEqual[B, A]): Boolean = iterator.contains(x)

      inline def indexOf_safe[B >: A](x: B)
                                     (using CanEqual[A, B], CanEqual[B, A]): Int = iterator.indexOf(x)

      inline def sameElements_safe[B >: A](that: IterableOnce[B])
                                          (using CanEqual[A, B], CanEqual[B, A]): Boolean = iterator.sameElements(that)

  trait TypesafeRootIteratorExtensionForOverloads:
    extension[A, S <: collection.Iterator[A]] (iterator: S)
      inline def indexOf_safe[B >: A](x: B, from: Int)
                                     (using CanEqual[A, B], CanEqual[B, A]): Int = iterator.indexOf(x, from)

end TypesafeIteratorExtension
