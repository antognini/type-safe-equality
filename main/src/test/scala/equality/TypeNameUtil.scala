package equality

import scala.quoted.*

object TypeNameUtil:

  inline def nameOf[A]: String = ${ nameOf[A] }

  def nameOf[A: Type](using Quotes): Expr[String] =
    import quotes.reflect.*

    val typeRepr = TypeRepr.of[A]
    Expr(typeRepr.show(using Printer.TypeReprShortCode))

