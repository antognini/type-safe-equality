package equality

// Experimental definitions

def hasEq[A: Eq](a:A): A = a

extension[T: Eq] (t: T)
  def onEq: T = t

object List_eq:
  def apply[T: Eq](elems: T*): Seq[T] = Seq(elems*)