package equality

import scala.quoted.{Expr, Quotes, Type}
import equality.*

object EqMacro:

  inline def derived[T]: Eq[T] =
    ${ derived[T] }

  def derived[T: Type](using quotes: Quotes): Expr[Eq[T]] =
    import quotes.reflect.*
    import quotes.reflect.report.errorAndAbort

    val typeRepr: TypeRepr = TypeRepr.of[T]

    extension (error: String)
      def abort: Nothing = errorAndAbort(s"Unable to derive Eq[${typeRepr.show}] because $error")

    typeRepr.asType match
      case '[Product] =>
        val typeSymbol = typeRepr.typeSymbol
        val typeParameters = typeSymbol.declaredTypes.map(_.name).toSet
        val implicitConstructorParameterTypeNames:Set[String] =
          val constructor = typeSymbol.primaryConstructor
          val constructorType = typeRepr.memberType(constructor) match
            case polyType: PolyType => polyType.resType
            case otherType => otherType

          constructorType match
            case methodType: MethodType =>
              val methodTypes = LazyList.iterate(Option(methodType)):
                case Some(currentType) => currentType.resType match
                  case resultType: MethodType => Some(resultType)
                  case _ => None
                case _ => None
              .takeWhile(_.isDefined).flatten
              methodTypes.filter(_.isImplicit).flatMap(_.paramTypes).map(_.dealias).map(_.show).toSet

        typeSymbol.caseFields foreach: fieldSymbol =>
          val fieldType = typeRepr.dealias.memberType(fieldSymbol)
          val fieldTypeName = fieldType.typeSymbol.name
          val fullyQualifiedFieldName = fieldType.show(using Printer.TypeReprShortCode)

          fieldType.asType match
            case '[f] =>
              if typeParameters.contains(fieldTypeName) then
                val equalityTypeName = TypeRepr.of[Eq[f]].dealias.show

                if !implicitConstructorParameterTypeNames.contains(equalityTypeName) then
                  s"neither context bound [$fullyQualifiedFieldName: Eq] nor implicit constructor parameter of Eq[$fullyQualifiedFieldName] is declared".abort

              if Expr.summon[Eq[f]].isEmpty then
                s"given instance of Eq[$fullyQualifiedFieldName] is not available in the current scope".abort
      case _ =>
        s"${typeRepr.show} is not a product type".abort
    '{ Eq.instanceOf[T] }

end EqMacro