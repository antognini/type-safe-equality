package equality

export equality.scala_all.EqInstances.given
export equality.scala_all.EqInstances.{AnyNumber, AnyJavaNumber}

export equality.java_all.EqInstances.given

sealed trait Eq[-T]:
  override def toString = "Eq"

object Eq:
  inline def derived[T]: Eq[T] = EqMacro.derived

  private[equality] def instanceOf[T]: Eq[T] = Instance
  private object Instance extends Eq[Any]

  sealed trait assumed[-T] extends Eq[T]:
    override def toString = "Eq.assumed"

  object assumed extends assumed[Any]:
    def derived[T]: assumed[T] = assumed
    override def toString = "Eq.assumed"

end Eq
