package equality.core

import scala.quoted.{Expr, Quotes, Type}

private[equality] object EqMacro:

  inline def derived[T]: Eq[T] =
    ${ derivedMacro[T] }

  def derivedMacro[T: Type](using quotes: Quotes): Expr[Eq[T]] =

    import quotes.reflect.{TypeRepr, report}

    val typeRepr = TypeRepr.of[T]
    val violations = EqReflection(typeRepr).violations

    if typeRepr <:< TypeRepr.of[ProductTest] then
      // Continue the compilation and store the violations in the type class instance for unit test evaluation
      '{ Eq[T](${ Expr(violations) }) }
    else
      violations foreach report.error
      '{ Eq[T] }

end EqMacro
