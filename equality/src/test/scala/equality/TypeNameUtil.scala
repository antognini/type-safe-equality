package equality

import scala.quoted.{Expr, Quotes, Type, quotes}

object TypeNameUtil:

  inline def nameOf[T]: String = ${ nameOf[T] }

  def nameOf[T: Type](using Quotes): Expr[String] =
    import quotes.reflect.{Printer, TypeRepr}

    val typeRepr = TypeRepr.of[T]
    Expr(typeRepr.show(using Printer.TypeReprShortCode))
