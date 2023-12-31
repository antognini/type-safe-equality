package equality.core

import scala.quoted.{Expr, Quotes, Type}
import TypeSupport.{extractTypeParameters, wildcardTypeParameter}

private[equality] class EqReflection(using val quotes: Quotes)(typeRepr: quotes.reflect.TypeRepr):
  import quotes.reflect.*

  lazy val violations: Seq[String] =
    if isProduct then
      val summonErrors =
        fields
        .filterNot(_.canSummonEq)
        .map(_.typeName)
        .distinct
        .map(wildcardTypeParameters)
        .map: typeName =>
          s"Values of types $typeName and $typeName cannot be compared with == or !="

      val contextBoundErrors =
        fields.flatMap: field =>
          field.typeParameters.collect:
            // Double-check if the extracted type parameter is really declared
            case typeParameter if typeParameters.contains(typeParameter) && !hasImplicitEqParameter(typeParameter) => typeParameter
        .distinct
        .map: typeParameter =>
          s"Neither context bound [$typeParameter: Eq] nor constructor parameter (using Eq[$typeParameter]) is defined"

      (summonErrors ++ contextBoundErrors)
    else
      Seq(s"$typeName is not a product type")

  private lazy val typeName = nameFor(typeRepr)

  private lazy val fields: Seq[Field] =
    typeRepr.typeSymbol.caseFields.map: fieldSymbol =>
      typeRepr
        .dealias
        .memberType(fieldSymbol)
    .map(Field.apply)

  private lazy val implicitParamTypes: Seq[ImplicitParamType] =
    val constructor = typeRepr.typeSymbol.primaryConstructor
    val constructorType = typeRepr.memberType(constructor) match
      case polyType: PolyType => polyType.resType
      case otherType => otherType

    val paramTypes: Seq[TypeRepr] = constructorType match
      case methodType: MethodType =>
        val methodTypes = LazyList.iterate(Option(methodType)):
          case Some(currentType) => currentType.resType match
            case resultType: MethodType => Some(resultType)
            case _ => None
          case _ => None
        .takeWhile(_.isDefined).flatten
        methodTypes
          .filter(_.isImplicit)
          .flatMap(_.paramTypes)
          .map(_.dealias)
    paramTypes.map(ImplicitParamType.apply)

  private lazy val typeParameters: Set[String] =
    typeRepr.typeSymbol.declaredTypes.map(_.name).toSet

  private def isProduct =
    given Quotes = quotes

    typeRepr.asType match
      case '[Product] => true
      case _ => false

  private def hasImplicitEqParameter(typeName: String) =
    implicitParamTypes.exists(_.isEqForType(typeName))

  private def wildcardTypeParameters(typeName: String): String =
    typeParameters.fold(typeName):
      (tpe, typeParameter) => tpe.wildcardTypeParameter(typeParameter)

  private def nameFor(typeRepr: TypeRepr) =
    typeRepr.show(using Printer.TypeReprShortCode)

  private case class Field(typeRepr: TypeRepr)(using Quotes):
    lazy val typeName: String = nameFor(typeRepr)
    lazy val typeParameters: Seq[String] = typeName.extractTypeParameters
    lazy val canSummonEq: Boolean = typeRepr.asType match
      case '[f] => Expr.summon[Eq[f]].nonEmpty

  private case class ImplicitParamType(typeRepr: TypeRepr):
    lazy val typeName = nameFor(typeRepr)
    def isEqForType(tpe: String) = typeName.endsWith(s"Eq[$tpe]")
