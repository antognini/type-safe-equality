package equality

private[equality] object TypeParameterSupport:

  private val separator = """\s*[,\[(\])]+\s*""".r
  private val replacement = "$1?$2"

  extension (tpe: String)
    def extractTypeParams: Seq[String] =
      separator.split(tpe).distinct.filter(!_.contains(".")).toSeq

    def replaceTypeParameterWithWildcard(typeParameter: String): String =
      val pattern = s"($separator)$typeParameter($separator)".r
      pattern.replaceAllIn(pattern.replaceAllIn(tpe, replacement), replacement)