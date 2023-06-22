package equality

import equality.TypesafeIterableOnceExtension.*

trait TypesafeIterableOnceExtension
  extends TypesafeRootIterableOnceExtension

case object TypesafeIterableOnceExtension extends TypesafeIterableOnceExtension:

  trait TypesafeRootIterableOnceExtension:
    extension[A, I <: collection.IterableOnce[A]] (iterableOnce: I)
      @deprecated("Use .iterator.sameElements_safe instead", "2.13.0")
      inline def sameElements_safe[B >: A](that: IterableOnce[B])
                                          (using CanEqual[A, B], CanEqual[B, A]): Boolean = iterableOnce.sameElements(that)

end TypesafeIterableOnceExtension